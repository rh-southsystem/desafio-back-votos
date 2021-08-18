package br.com.btr.desafiovotos.pauta.controller;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btr.desafiovotos.pauta.enums.Voto;
import br.com.btr.desafiovotos.pauta.json.Pauta;
import br.com.btr.desafiovotos.pauta.json.Sessao;
import br.com.btr.desafiovotos.pauta.service.PautaService;

@RestController
@RequestMapping("/pauta")
public class PautaController {

	@Autowired
	private PautaService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	private Pauta save(@Valid @RequestBody final Pauta pauta) {
		this.service.save(pauta);
		
		return pauta;
	}
	
	@PostMapping( "/{idPauta}/initialize" )
	private void initialize(@NotNull @PathVariable final Long idPauta, @Valid @RequestBody Sessao sessao) {
		this.service.initialize(idPauta,sessao);
	}

	@GetMapping( "/{idPauta}/result" )
	private Map<Voto, Integer> getResult(@NotNull @PathVariable final Long idPauta) {
		return this.service.getResult(idPauta);
	}
}
