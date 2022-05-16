package com.southsystem.controller;

import java.net.URI;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.southsystem.domain.Vote;
import com.southsystem.dto.VoteCreateDTO;
import com.southsystem.dto.VoteReadDTO;
import com.southsystem.service.VoteService;

@RestController
@RequestMapping(value = "/api/vote")
public class VoteController {
	
	@Autowired
	private VoteService voteService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping
	public ResponseEntity<VoteReadDTO> vote(
			@Valid @RequestBody VoteCreateDTO voteCreateDTO) {
		Vote vote = voteService.vote(voteCreateDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(vote.getId()).toUri();
		return ResponseEntity.created(uri)
				.body(modelMapper.map(vote, VoteReadDTO.class));
	}

}
