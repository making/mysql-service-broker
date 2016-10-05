package com.example;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.servicebroker.model.Catalog;
import org.springframework.cloud.servicebroker.model.Plan;
import org.springframework.cloud.servicebroker.model.ServiceDefinition;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MysqlServiceBrokerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MysqlServiceBrokerApplication.class, args);
	}

	@Bean
	Catalog catalog() {
		return new Catalog(Collections.singletonList(new ServiceDefinition("a", "v-mysql",
				"A vanilla MySQL service broker", true, false, Collections.singletonList(
						new Plan("b", "free", "free plan", new HashMap<String, Object>() {
							{
								put("costs", Collections
										.singletonList(new HashMap<String, Object>() {
											{
												put("amount", Collections
														.singletonMap("usd", 0.0));
												put("unit", "MONTHLY");
											}
										}));
								put("bullets", Arrays.asList("fake", "foo", "bar"));
							}
						}, true)),
				Arrays.asList("tag A", "tag B", "tag C"), new HashMap<String, Object>() {
					{
						put("displayName", "MySQL");
						put("longDescription", "MySQL Service");
						put("imageUrl",
								"https://www.cloudfoundry.org/wp-content/uploads/2015/10/CF_rabbit_Blacksmith_rgb_trans_back-269x300.png");
						put("providerDisplayName", "@making");
					}
				}, null, null)));
	}
}
