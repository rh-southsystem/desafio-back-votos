package com.southsystem.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.southsystem.domain.Assembly;
import com.southsystem.domain.Associate;
import com.southsystem.domain.Vote;
import com.southsystem.domain.VotePK;
import com.southsystem.domain.enums.AssemblyStatus;
import com.southsystem.domain.enums.AssociatePermission;
import com.southsystem.domain.enums.VoteChoice;
import com.southsystem.dto.VoteCreateDTO;
import com.southsystem.repository.VoteRepository;
import com.southsystem.service.exception.AssemblyNotStartedException;
import com.southsystem.service.exception.AssociateAlreadyVotedException;
import com.southsystem.service.exception.AssociateUnableToVoteException;

@Service
public class VoteService {
	
	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private AssemblyService assemblyService;
	
	@Autowired
	private AssociateService associateService;
	
	public Vote vote(VoteCreateDTO voteCreateDTO) {
		Assembly assembly = assemblyService.findById(voteCreateDTO.getAssembly());
		if (assembly.getStatus() != AssemblyStatus.STARTED.getId()) {
			throw new AssemblyNotStartedException();
		}
		
		Associate associate = associateService.findById(voteCreateDTO.getAssociate());
		if (associate.getPermission() == AssociatePermission.UNABLE_TO_VOTE.getId()) {
			throw new AssociateUnableToVoteException();
		}
		
		VoteChoice voteChoice = VoteChoice.toEnum(voteCreateDTO.getVote());
		VotePK votePK = new VotePK(assembly, associate);
		Optional<Vote> optionalVote = voteRepository.findById(votePK);
		if (!optionalVote.isEmpty()) {
			throw new AssociateAlreadyVotedException();
		}
		Vote vote = new Vote(votePK, voteChoice.getId(), LocalDateTime.now());
		
		return voteRepository.save(vote);
	}

}
