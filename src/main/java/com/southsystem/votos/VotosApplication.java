package com.southsystem.votos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableSqs
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class VotosApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotosApplication.class, args);
	}

}
