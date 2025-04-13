package com.creditagricole.messageroutingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MessageRoutingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageRoutingAppApplication.class, args);
	}

}
