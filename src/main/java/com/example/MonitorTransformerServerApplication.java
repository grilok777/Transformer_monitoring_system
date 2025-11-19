package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

//579af6c1-3665-4633-a738-ad929fa3c11e

@EnableAsync
@SpringBootApplication
public class MonitorTransformerServerApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(MonitorTransformerServerApplication.class, args);

		StarterSimulator starterService = context.getBean(StarterSimulator.class);
		starterService.startActiveTransformers();
	}
}