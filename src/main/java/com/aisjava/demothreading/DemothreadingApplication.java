package com.aisjava.demothreading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync   // enables asynchronous processing with @Async
public class DemothreadingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemothreadingApplication.class, args);
	}

}
