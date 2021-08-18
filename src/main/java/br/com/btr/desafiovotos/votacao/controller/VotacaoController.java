package br.com.btr.desafiovotos.votacao.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btr.desafiovotos.votacao.json.Votacao;
import br.com.btr.desafiovotos.votacao.service.VotacaoService;

@RestController
@RequestMapping("/votacao")
public class VotacaoController {

	@Autowired
	private VotacaoService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	private ResponseEntity<?> vote(@Valid @RequestBody final Votacao voto) {
		try {
			this.service.vote(voto);
			
			return ResponseEntity.ok("Voto recebido com sucesso!");
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
