package br.com.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dto.IniciarPautaDTO;
import br.com.dto.PautaDTO;
import br.com.entity.Pauta;
import br.com.service.PautaService;

@RestController
@RequestMapping("/pauta")
public class PautaController {

	    @Autowired
	    PautaService service;

	    @PostMapping({"/v1.0"})
	    public ResponseEntity<Pauta> criarPauta(@Valid @RequestBody PautaDTO pautaDTO){
	    	return ResponseEntity.ok().body(service.add(pautaDTO));
	    }
	    
	    @PostMapping({"/v1.0/iniciar/{id}"})
	    public ResponseEntity<String> iniciarPauta(@PathVariable Long id ,@RequestBody IniciarPautaDTO iniciarPautaDTO){
	    	return ResponseEntity.ok().body(service.iniciar(id,iniciarPautaDTO));
	    }
	    
	    @GetMapping({"/v1.0", "/v1.1"})
	    public ResponseEntity<List<Pauta>> findAll(){
	        return ResponseEntity.ok().body(service.obterTodos());
	    }
}
