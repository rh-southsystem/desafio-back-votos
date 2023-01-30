package br.com.assembliescorp.infra.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@OpenAPIDefinition
@Configuration
public class SwaggerConfiguration {
	 @Bean
	    public OpenAPI apiInfo() {
	        return new OpenAPI().info(new Info().title("Teste").version("1.0.0").description("Teste"));
	    }
}
