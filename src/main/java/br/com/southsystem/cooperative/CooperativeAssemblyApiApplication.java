package br.com.southsystem.cooperative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableKafka
@SpringBootApplication
public class CooperativeAssemblyApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CooperativeAssemblyApiApplication.class, args);
    }

}
