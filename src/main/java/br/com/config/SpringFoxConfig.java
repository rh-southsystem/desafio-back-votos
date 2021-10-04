package br.com.config;


import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {                                    

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
				.paths(Predicates.not(PathSelectors.regex("/error"))).build().apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
				.genericModelSubstitutes(Optional.class);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Votação API").description("Sistema de votação").termsOfServiceUrl("").licenseUrl("none")
				.version("Thiago Rodrigues").build();
	}
	
	@Bean
	public Docket swaggerPautaApi1() {
	    return new Docket(DocumentationType.SWAGGER_2)
	        .groupName("pauta-api-1.0")
	        .select()
	            .apis(RequestHandlerSelectors.basePackage("br.com.controller"))
	            .paths(PathSelectors.regex("/pauta/v1.0.*"))
	        .build()
	        .apiInfo(new ApiInfoBuilder().version("1.0").title("Votação API").description("Versão da Chamadas da Pauta").build());
	}
	
	@Bean
	public Docket swaggerPautaApi11() {
	    return new Docket(DocumentationType.SWAGGER_2)
	        .groupName("pauta-api-1.1")
	        .select()
	            .apis(RequestHandlerSelectors.basePackage("br.com.controller"))
	            .paths(PathSelectors.regex("/pauta/v1.1.*"))
	        .build()
	        .apiInfo(new ApiInfoBuilder().version("1.1").title("Votação API").description("Versão da Chamadas da Pauta").build());
	}
	
	@Bean
	public Docket swaggerVotoApi1() {
	    return new Docket(DocumentationType.SWAGGER_2)
	        .groupName("voto-api-1.0")
	        .select()
	            .apis(RequestHandlerSelectors.basePackage("br.com.kafka.producer.controller"))
	            .paths(PathSelectors.regex("/voto/v1.0.*"))
	        .build()
	        .apiInfo(new ApiInfoBuilder().version("1.0").title("Votação API").description("Versão da Chamadas da Voto").build());
	}
 
}
