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

import com.southsystem.domain.Assembly;
import com.southsystem.dto.AssemblyCreateDTO;
import com.southsystem.dto.AssemblyReadDTO;
import com.southsystem.dto.AssemblyUpdateDTO;
import com.southsystem.service.AssemblyService;

@RestController
@RequestMapping(value = "/api/assembly")
public class AssemblyController {
	
	@Autowired
	private AssemblyService assemblyService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<AssemblyReadDTO> create(
			@Valid @RequestBody AssemblyCreateDTO assemblyCreateDTO) {
		Assembly assembly = assemblyService.create(assemblyCreateDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(assembly.getId()).toUri();
		return ResponseEntity.created(uri)
				.body(modelMapper.map(assembly, AssemblyReadDTO.class));
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<AssemblyReadDTO> findById(@PathVariable Integer id) {
		Assembly assembly = assemblyService.findById(id);
		return ResponseEntity.ok(modelMapper.map(assembly, AssemblyReadDTO.class));
	}
	
	@GetMapping
	public ResponseEntity<Page<AssemblyReadDTO>> list(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="10") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="title") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction,
			@RequestParam(value="title", defaultValue="") String title) {
		return ResponseEntity.ok(assemblyService.list(page, linesPerPage, orderBy, 
				direction, title));
	}
	
	@PutMapping
	public ResponseEntity<AssemblyReadDTO> update(
			@Valid @RequestBody AssemblyUpdateDTO assemblyUpdateDTO) {
		Assembly assembly = assemblyService.update(assemblyUpdateDTO);
		return ResponseEntity.ok(modelMapper.map(assembly, AssemblyReadDTO.class));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		assemblyService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/startVoting/{id}")
	public ResponseEntity<AssemblyReadDTO> startVoting(@PathVariable Integer id) {
		Assembly assembly = assemblyService.startVoting(id);
		return ResponseEntity.ok(modelMapper.map(assembly, AssemblyReadDTO.class));
	}

}
