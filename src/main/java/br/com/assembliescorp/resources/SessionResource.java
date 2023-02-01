package br.com.assembliescorp.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.assembliescorp.domain.dtos.session.SessionCreateDTO;
import br.com.assembliescorp.domain.dtos.session.SessionListDTO;
import br.com.assembliescorp.services.SessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Sessão")
@RestController
@RequestMapping("api/v1/sesson")
public class SessionResource {

	private final SessionService service;

	@Autowired
	public SessionResource(SessionService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<SessionListDTO>> getPageable() {
		return ResponseEntity.ok(this.service.getList());
	}

	@PostMapping
	public ResponseEntity<SessionCreateDTO> create(@Valid @RequestBody SessionCreateDTO sessionCreateDTO,
			UriComponentsBuilder uriBuilder) {
		var session = service.create(sessionCreateDTO);
		var uri = uriBuilder.path("api/v1/associate/{id}").buildAndExpand(session.id()).toUri();
		return ResponseEntity.created(uri).body(session);
	}

}
