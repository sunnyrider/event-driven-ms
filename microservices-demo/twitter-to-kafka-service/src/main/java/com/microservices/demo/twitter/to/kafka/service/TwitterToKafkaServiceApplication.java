package com.microservices.demo.twitter.to.kafka.service;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.microservices.demo.twitter.to.kafka.service.config.TwitterToKafkaServiceConfigData;
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner;

@SpringBootApplication
public class TwitterToKafkaServiceApplication implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterToKafkaServiceApplication.class);

	private final TwitterToKafkaServiceConfigData twitterToKafkaServiceConfigData;
	private final StreamRunner streamRunner;

	public TwitterToKafkaServiceApplication(TwitterToKafkaServiceConfigData configData, 
			StreamRunner runner) {
		twitterToKafkaServiceConfigData = configData;
		streamRunner = runner;
	}

	public static void main(String[] args) {
		SpringApplication.run(TwitterToKafkaServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("App starts ...");
		LOGGER.info(Arrays.toString(twitterToKafkaServiceConfigData.getTwitterKeywords().toArray(new String[] {})));
		LOGGER.info(twitterToKafkaServiceConfigData.getWelcomeMessage());
		streamRunner.start();
	}
}
