package com.southsystem.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
	
	private ModelMapper modelMapper = new ModelMapper();

	@Bean
	public ModelMapper getModelMapper() {
		return modelMapper;
	}
}
