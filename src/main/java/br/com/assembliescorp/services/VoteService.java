package br.com.assembliescorp.services;

import org.springframework.stereotype.Service;

import br.com.assembliescorp.domain.dtos.VoteProcess;
import br.com.assembliescorp.domain.dtos.vote.VoteDTO;

@Service
public interface VoteService {
	
	VoteDTO vote(VoteDTO voteDTO);
	void process(VoteProcess voteProcess);

}
