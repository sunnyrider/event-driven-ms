package com.microservices.demo.twitter.to.kafka.service.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "twitter-to-kafka-service")
public class TwitterToKafkaServiceConfigData {

	private List<String> twitterKeywords;
	private String welcomeMessage;
//	private String enableV2Tweets;
	private String twitterV2BaseUrl;
	private String twitterV2RulesBaseUrl;
	private String twitterV2BearerToken;
}
