package br.com.assembliescorp.services.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.assembliescorp.services.SendRabbitService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SendRabbitServiceImpl implements SendRabbitService {
	
	@Value("${rabbit.results_dx}")
	private String topicExchangeName;
	
	@Value("${rabbit.routing_key}")
	private String routingKey;
	
	private final RabbitTemplate rabbitTemplate;
	
	@Autowired
	public SendRabbitServiceImpl(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	
	public void sendRabbit(Object object) {
		log.info("ENVIANDO RESULTADO PARA ROTA {} E TOPICO {}", routingKey, topicExchangeName);
		rabbitTemplate.convertAndSend(topicExchangeName, routingKey, object);		
	}
}
