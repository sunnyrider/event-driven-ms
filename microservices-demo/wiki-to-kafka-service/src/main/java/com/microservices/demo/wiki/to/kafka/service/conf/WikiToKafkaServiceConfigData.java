package com.microservices.demo.wiki.to.kafka.service.conf;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "twitter-to-kafka-service")
public class WikiToKafkaServiceConfigData {

	private List<String> twitterKeywords;
	private String welcomeMessage;
}
