package br.com.southsystem.cooperative.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(metaInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.com.southsystem.cooperative.web.rest.v1"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo metaInfo() {
        return new ApiInfo(
                "Cooperative assembly services API",
                "This api offers services for managing a cooperative assembly",
                "1.0",
                "Terms of services",
                new Contact("Vinícius Cavalcanti", "", "viniciuscdst@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licence.html", new ArrayList<>()
        );
    }


}
