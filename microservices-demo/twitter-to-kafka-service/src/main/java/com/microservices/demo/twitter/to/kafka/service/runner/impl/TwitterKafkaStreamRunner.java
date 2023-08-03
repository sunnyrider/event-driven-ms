package com.microservices.demo.twitter.to.kafka.service.runner.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

import com.microservices.demo.twitter.to.kafka.service.config.TwitterToKafkaServiceConfigData;
import com.microservices.demo.twitter.to.kafka.service.listener.TwitterKafkaStatusListener;
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner;

import jakarta.annotation.PreDestroy;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

@Component
@ConditionalOnExpression("${twitter-to-kafka-service.enable-mock-tweets} && not ${twitter-to-kafka-service.enable-v2-tweets}")
public class TwitterKafkaStreamRunner implements StreamRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterKafkaStreamRunner.class);

	private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
	private final TwitterKafkaStatusListener twitterKafkaStatusListener;

	private TwitterStream twitterStream;

	public TwitterKafkaStreamRunner(TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData,
			TwitterKafkaStatusListener twitterKafkaStatusListener) {
		this.twitterToKafkaServiceConfigData = twitterToKafkaServiceConfigData;
		this.twitterKafkaStatusListener = twitterKafkaStatusListener;
	}

	@Override
	public void start() throws TwitterException {
		twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(twitterKafkaStatusListener);
		addFilter();
		try {
			LOGGER.info("Calling SLEEP 4 Seconds ...");
			Thread.sleep(30000);
			LOGGER.info("Calling SHUTDOWN after sleep ...");
			shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@PreDestroy
	public void shutdown() {
		if (twitterStream != null) {
			LOGGER.info("Closing twitter stream!");
			twitterStream.shutdown();
		}
	}

	private void addFilter() {
		String[] keywords = twitterToKafkaServiceConfigData.getTwitterKeywords().toArray(new String[0]);
		FilterQuery filterQuery = new FilterQuery(keywords);
		twitterStream.filter(filterQuery);
		LOGGER.info("Started filtering twitter stream for keywords {}", Arrays.toString(keywords));
	}
}
