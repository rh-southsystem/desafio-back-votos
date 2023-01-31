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

import br.com.assembliescorp.domain.dtos.ruling.RulingCreateDTO;
import br.com.assembliescorp.domain.dtos.ruling.RulingListDTO;
import br.com.assembliescorp.services.RulingService;

@RestController
@RequestMapping("api/v1/ruling")
public class RulingResource {
	
	public final RulingService rulingService;
	
	@Autowired
	public RulingResource(RulingService rulingService) {
		this.rulingService = rulingService;
	}
	
	@GetMapping
	public ResponseEntity<List<RulingListDTO>> getPageable(){
		return ResponseEntity.ok(this.rulingService.getList());
	}
		
	
	@PostMapping
	public ResponseEntity<RulingCreateDTO> create(@RequestBody RulingCreateDTO rulingCreateDTO, UriComponentsBuilder uriBuilder) {	
		var ruling = rulingService.create(rulingCreateDTO);
        var uri = uriBuilder.path("api/v1/associate/{id}").buildAndExpand(ruling.id()).toUri();
        return ResponseEntity.created(uri).body(ruling);       		
	}

}
