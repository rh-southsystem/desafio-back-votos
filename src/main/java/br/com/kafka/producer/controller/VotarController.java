package br.com.kafka.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dto.VotoDTO;
import br.com.service.VotoService;

@RestController
@RequestMapping("/voto")
public class VotarController {

    @Autowired
    VotoService votoService;

    @PostMapping({"/v1.0"})
    public ResponseEntity<String> enviarMensagem(@RequestBody VotoDTO voto){
    	
    	votoService.validaVoto(voto);
    	votoService.sendMessage(voto.toJson());
        return ResponseEntity.ok().body("Voto Computado: " + voto.toJson());
    }
}