package br.com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;


@ComponentScan(basePackages = { "br.com" })
@EnableJpaRepositories(basePackages = { "br.com" })
@EntityScan(basePackages = { "br.com" })
@SpringBootApplication
@EnableCaching
public class APIPautaEVotacao {

	public static void main(String[] args) {
		SpringApplication.run(APIPautaEVotacao.class, args);
	}
	
	@Bean
	RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
	@Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("pauta");
    }
}
