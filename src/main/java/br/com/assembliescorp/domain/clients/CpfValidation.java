package br.com.assembliescorp.domain.clients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class CpfValidation {
	
	private RestTemplate restTemplate;
	
	@Value("${resttemplate.cpfurl}")
	private String url;
	
	public CpfValidation(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}
	
	public String getValidationCpf(String cpf) {
		String uri = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("cpf", cpf).build().toString();
		
		return restTemplate.getForEntity(uri, String.class).getBody();
	}

}
