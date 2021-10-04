package br.com.kafka.consumer.listener;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import br.com.service.VotoService;

@Service
public class VotoConsumer {

    @Autowired
    VotoService votoService;

    @KafkaListener(topics = "${topic.voto}", groupId = "group_id")
    public void consume(String voto ) throws IOException {
    	votoService.computarVoto(voto);
       
    }
}