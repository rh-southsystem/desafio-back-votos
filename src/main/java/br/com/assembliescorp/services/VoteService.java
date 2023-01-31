package br.com.assembliescorp.services;

import org.springframework.stereotype.Service;

import br.com.assembliescorp.domain.dtos.vote.VoteDTO;
import br.com.assembliescorp.domain.dtos.vote.VoteProcess;

@Service
public interface VoteService {
	
	VoteDTO vote(VoteDTO voteDTO);
	void process(VoteProcess voteProcess);

}
