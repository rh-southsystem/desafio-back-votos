package br.com.assembliescorp.services.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.assembliescorp.services.SendRabbitService;

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
		rabbitTemplate.convertAndSend(topicExchangeName, routingKey, object);		
	}
}
