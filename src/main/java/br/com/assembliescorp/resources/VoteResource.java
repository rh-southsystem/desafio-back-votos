package br.com.assembliescorp.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.assembliescorp.domain.dtos.vote.VoteDTO;
import br.com.assembliescorp.domain.dtos.vote.VoteProcess;
import br.com.assembliescorp.services.VoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Votação")
@RestController
@RequestMapping("api/v1/vote")
public class VoteResource {
	
	private final VoteService voteService;
	
	@Autowired
	public VoteResource(VoteService voteService) {
		this.voteService = voteService;
	}
	
	@Operation(summary = "Votação",description = "Realiza uma votação por sessão e associado")
	@PostMapping
	public ResponseEntity<VoteDTO> vote(@Valid @RequestBody VoteDTO vote, UriComponentsBuilder uriBuilder) {
		var voteDto = voteService.vote(vote);
        var uri = uriBuilder.path("api/v1/associate/{id}").buildAndExpand(voteDto.id()).toUri();
        return ResponseEntity.created(uri).body(voteDto);       		
	}
	
	@Operation(summary = "Apuração de Votos",description = "Processa os votos por sessão. Fim do processo")
	@PostMapping("/process")
	public ResponseEntity<Void> process(@RequestBody VoteProcess voteProcess) {
		voteService.process(voteProcess);
		return ResponseEntity.noContent().build();
	}

}
