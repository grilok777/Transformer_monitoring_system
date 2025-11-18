
package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//579af6c1-3665-4633-a738-ad929fa3c11e
@SpringBootApplication
public class MonitorTransformerServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitorTransformerServerApplication.class, args);
	}

}
/*
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//579af6c1-3665-4633-a738-ad929fa3c11e
@EnableMongoRepositories(basePackages = "com.example.repository.mongo")
@SpringBootApplication
public class MonitorTransformerServerApplication {

	public static void main(String[] args) {

		ApplicationContext context =
				SpringApplication.run(MonitorTransformerServerApplication.class, args);

		// запуск сервісу, що активує трансформатори
		StarterSimulator starterService = context.getBean(StarterSimulator.class);
		starterService.startActiveTransformers();
	}

}*/