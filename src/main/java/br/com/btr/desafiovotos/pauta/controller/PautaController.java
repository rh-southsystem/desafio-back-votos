package br.com.btr.desafiovotos.pauta.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.btr.desafiovotos.pauta.json.Pauta;
import br.com.btr.desafiovotos.pauta.json.Sessao;
import br.com.btr.desafiovotos.pauta.service.PautaService;

@RestController
@RequestMapping("/pauta/v1")
public class PautaController {

	@Autowired
	private PautaService service;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	private ResponseEntity<?> save(@Valid @RequestBody final Pauta pauta) {
		try {
			this.service.save(pauta);

			return ResponseEntity.ok(pauta);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PostMapping("/{idPauta}/initialize")
	private ResponseEntity<?> initialize(@NotNull @PathVariable final Long idPauta, @Valid @RequestBody Sessao sessao) {
		try {
			this.service.initialize(idPauta, sessao);

			return ResponseEntity.ok().build();
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@GetMapping("/{idPauta}/result")
	private ResponseEntity<?> getResult(@NotNull @PathVariable final Long idPauta) {
		try {
			return ResponseEntity.ok(this.service.getResult(idPauta));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
}
