package br.com.service;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.response.CpfResponse;

@Component
public class ConsultaCPFService {

	@Autowired
	RestTemplate restTemplate;
	 
	 @Value("${url.cpf}") 
	String  urlServicoCPF;
	
	
	public boolean permiteVotacao(Long cpf) {
		
		try {
			CpfResponse resultado = restTemplate.getForObject(urlServicoCPF.concat("/").concat(cpf.toString()),  CpfResponse.class);
			return  "ABLE_TO_VOTE".equals(resultado.getStatus());
			} catch (Exception e) {
				throw new ServiceException("Ocorreu um erro ao consultar o CPF.");
			}	
	}

}
