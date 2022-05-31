package br.com.southsystem.assembleia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class AssembleiaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssembleiaApplication.class, args);
	}

}
