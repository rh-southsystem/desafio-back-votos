package br.com.assembliescorp;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class AssembliescorpApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssembliescorpApplication.class, args);
	}

}
