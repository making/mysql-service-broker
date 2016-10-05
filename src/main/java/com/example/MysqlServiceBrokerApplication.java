package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

@SpringBootApplication
public class MysqlServiceBrokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysqlServiceBrokerApplication.class, args);
	}

	@Bean
	Catalog catalog(@Value("classpath:catalog.yml") Resource resource) throws Exception {
		return new ObjectMapper(new YAMLFactory()).readValue(resource.getInputStream(),
				Catalog.class);
	}
}
