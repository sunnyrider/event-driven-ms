package com.microservices.demo.wiki.to.kafka.service.producer;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import com.microservices.demo.wiki.to.kafka.service.event.WikimediaChangesHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {
	private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    @Value("${spring.kafka.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {
        // to read real time stream data from wikimedia, we use event source
        EventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topicName);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource = builder.build();

        LOGGER.info("Start publishing to kafka topic {}", topicName);
        eventSource.start();

        TimeUnit.SECONDS.sleep(30);
        LOGGER.info("Shutdown publisher!");
        shutdown(eventSource);
    }
    
    public void shutdown(EventSource src) {
    	if (src != null) {
    		src.close();
    	}
    }
}
