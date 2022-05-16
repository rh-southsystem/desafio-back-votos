package com.southsystem.controller;

import java.net.URI;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.southsystem.domain.Associate;
import com.southsystem.dto.AssociateCreateDTO;
import com.southsystem.dto.AssociateReadDTO;
import com.southsystem.dto.AssociateUpdateDTO;
import com.southsystem.service.AssociateService;

@RestController
@RequestMapping(value = "/api/associate")
public class AssociateController {
	
	@Autowired
	private AssociateService associateService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<AssociateReadDTO> create(
			@Valid @RequestBody AssociateCreateDTO associateCreateDTO) {
		Associate associate = associateService.create(associateCreateDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(associate.getId()).toUri();
		return ResponseEntity.created(uri)
				.body(modelMapper.map(associate, AssociateReadDTO.class));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<AssociateReadDTO> findById(@PathVariable Integer id) {
		Associate associate = associateService.findById(id);
		return ResponseEntity.ok(modelMapper.map(associate, AssociateReadDTO.class));
	}
	
	@GetMapping
	public ResponseEntity<Page<AssociateReadDTO>> list(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="10") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="name") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction,
			@RequestParam(value="name", defaultValue="") String name) {
		return ResponseEntity.ok(associateService.list(page, linesPerPage, orderBy, 
				direction, name));
	}
	
	@PutMapping
	public ResponseEntity<AssociateReadDTO> update(
			@Valid @RequestBody AssociateUpdateDTO associateUpdateDTO) {
		Associate associate = associateService.update(associateUpdateDTO);
		return ResponseEntity.ok(modelMapper.map(associate, AssociateReadDTO.class));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		associateService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
