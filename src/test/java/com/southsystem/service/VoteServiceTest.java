package com.southsystem.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.southsystem.domain.Assembly;
import com.southsystem.domain.Associate;
import com.southsystem.domain.Vote;
import com.southsystem.domain.VotePK;
import com.southsystem.domain.enums.AssemblyStatus;
import com.southsystem.domain.enums.VoteChoice;
import com.southsystem.dto.VoteCreateDTO;
import com.southsystem.repository.VoteRepository;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {

	@Mock
	private VoteRepository voteRepository;

	@Mock
	private AssemblyService assemblyService;

	@Mock
	private AssociateService associateService;

	@InjectMocks
	private VoteService voteService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@DisplayName("Validate vote method")
    @Test
    public void testVote() {
		Assembly startedAssembly = new Assembly(1, "Assembly 1", "Gathering",
				AssemblyStatus.STARTED.getId(), LocalDateTime.now(), LocalDateTime.now(),
				LocalDateTime.now(), null, null, null, 60000L);
		
		Associate associate = new Associate(1, "92836845082", "João", LocalDateTime.now(), null);
    	when(assemblyService.findById(any(Integer.class))).thenReturn(startedAssembly);
    	when(associateService.findById(any(Integer.class))).thenReturn(associate);
    	when(voteRepository.findById(any(VotePK.class))).thenReturn(Optional.empty());
    	
    	VotePK votePK = new VotePK(startedAssembly, associate);
    	Vote vote = new Vote(votePK, VoteChoice.YES.getId(), LocalDateTime.now());
    	when(voteRepository.save(any(Vote.class))).thenReturn(vote);
    	
    	VoteCreateDTO voteCreateDTO = new VoteCreateDTO(1, 1, 1);
    	Vote voteCreated = voteService.vote(voteCreateDTO);
    	Assertions.assertNotNull(voteCreated.getId());
    	Assertions.assertNotNull(voteCreated.getCreationDate());
    }
}
