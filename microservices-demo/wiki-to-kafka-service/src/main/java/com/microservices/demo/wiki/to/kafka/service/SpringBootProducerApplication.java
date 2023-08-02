package com.microservices.demo.wiki.to.kafka.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.microservices.demo.wiki.to.kafka.service.producer.WikimediaChangesProducer;

@SpringBootApplication
public class SpringBootProducerApplication implements CommandLineRunner {
	private final WikimediaChangesProducer wikimediaChangesProducer;

    public SpringBootProducerApplication(WikimediaChangesProducer wikimediaChangesProducer) {
		this.wikimediaChangesProducer = wikimediaChangesProducer;
	}

	public static void main(String[] args) {
        SpringApplication.run(SpringBootProducerApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        wikimediaChangesProducer.sendMessage();
    }
}
