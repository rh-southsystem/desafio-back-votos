package com.southsystem.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.southsystem.domain.Assembly;
import com.southsystem.domain.Vote;
import com.southsystem.domain.enums.AssemblyStatus;
import com.southsystem.domain.enums.VoteChoice;
import com.southsystem.dto.AssemblyCreateDTO;
import com.southsystem.dto.AssemblyReadDTO;
import com.southsystem.dto.AssemblyUpdateDTO;
import com.southsystem.repository.AssemblyRepository;
import com.southsystem.repository.VoteRepository;
import com.southsystem.service.exception.AssemblyNotStartedException;
import com.southsystem.service.exception.CannotUpdateAssemblyException;
import com.southsystem.service.exception.EntityNotFoundException;
import com.southsystem.service.exception.InvalidAssemblyToStartVotingException;

@Service
public class AssemblyService {
	
	@Autowired
	private AssemblyRepository assemblyRepository;
	
	@Autowired
	private VoteRepository voteRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AssemblyService.class);
	
	public Assembly create(AssemblyCreateDTO assemblyCreateDTO) {
		LOGGER.info("Creating new assembly...");
		Assembly assembly = modelMapper.map(assemblyCreateDTO, Assembly.class);
		assembly.setCreationDate(LocalDateTime.now());
		assembly.setStatus(AssemblyStatus.PENDING.getId());
		assembly = assemblyRepository.save(assembly);
		
		LOGGER.info("Assembly created successfully. Returning value");
		return assembly;
	}
	
	public Assembly findById(Integer id) {
		LOGGER.info("Searching assembly...");
		Optional<Assembly> optionalAssembly = assemblyRepository.findById(id);
		if (optionalAssembly.isEmpty()) {
			LOGGER.error("Assembly not found");
			throw new EntityNotFoundException();
		}
		
		LOGGER.info("Assembly found. Returning value");
		return optionalAssembly.get();
	}
	
	public Page<AssemblyReadDTO> list(Integer page, Integer linesPerPage, String orderBy,
			String direction, String title) {
		LOGGER.info("Listing assemblies...");
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,
				Direction.valueOf(direction), orderBy);
		
		Page<AssemblyReadDTO> assemblies = assemblyRepository.findByTitleContainsIgnoreCase(title, pageRequest)
				.map(associate -> modelMapper.map(associate, AssemblyReadDTO.class));
		LOGGER.info("Assemblies listed successfully. Returning value");
		return assemblies;
	}
	
	public Page<Assembly> listByStatus(Integer page, Integer linesPerPage, String orderBy,
			String direction, AssemblyStatus status) {
		LOGGER.info("Listing assemblies by status...");
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,
				Direction.valueOf(direction), orderBy);
		
		Page<Assembly> assemblies = assemblyRepository.findByStatus(status.getId(), pageRequest);
		LOGGER.info("Assemblies listed successfully. Returning value");
		return assemblies;
	}
	
	public Assembly update(AssemblyUpdateDTO assemblyUpdateDTO) {
		LOGGER.info("Updating assembly...");
		Assembly assembly = findById(assemblyUpdateDTO.getId());
		
		if (assembly.getStatus() != AssemblyStatus.PENDING.getId()) {
			LOGGER.error("Assembly is pending. Cannot be updated");
			throw new CannotUpdateAssemblyException();
		}
		
		assembly.setTitle(assemblyUpdateDTO.getTitle());
		assembly.setDescription(assemblyUpdateDTO.getDescription());
		assembly.setDuration(assemblyUpdateDTO.getDuration());
		assembly.setUpdateDate(LocalDateTime.now());
		
		assembly = assemblyRepository.save(assembly);
		LOGGER.info("Assembly updated successfully. Returning value");
		return assembly;
	}
	
	public void delete(Integer id) {
		findById(id);
		LOGGER.info("Deleting assembly votes...");
		voteRepository.deleteByIdAssemblyId(id);
		LOGGER.info("Votes deleted. Deleting assembly...");
		assemblyRepository.deleteById(id);
		LOGGER.info("Assembly deleted successfully");
	}
	
	public Assembly startVoting(Integer id) {
		LOGGER.info("Starting voting...");
		Assembly assembly = findById(id);
		if (assembly.getStatus() != AssemblyStatus.PENDING.getId()) {
			LOGGER.error("Assembly is not pending. Cannot be voted");
			throw new InvalidAssemblyToStartVotingException();
		}
		
		assembly.setStatus(AssemblyStatus.STARTED.getId());
		assembly.setStartDate(LocalDateTime.now());
		assembly.setUpdateDate(LocalDateTime.now());
		
		assembly = assemblyRepository.save(assembly);
		LOGGER.info("Voting started successfully. Returning value");
		return assembly;
	}
	
	public Assembly finishVoting(Assembly assembly) {
		LOGGER.info("Finishing voting...");
		if (assembly.getStatus() != AssemblyStatus.STARTED.getId()) {
			throw new AssemblyNotStartedException();
		}
		
		AssemblyStatus status = _calculateVoting(assembly);
		assembly.setStatus(status.getId());
		assembly.setFinishDate(LocalDateTime.now());
		assembly.setUpdateDate(LocalDateTime.now());
		
		assembly = assemblyRepository.save(assembly);
		LOGGER.info("Voting calculation finished successfully. Returning value");
		return assembly;
	}
	
	private AssemblyStatus _calculateVoting(Assembly assembly) {
		LOGGER.info("Calculating votes for assembly: " + assembly.getId());
		List<Vote> votes = voteRepository.findByIdAssemblyId(assembly.getId());
		float yes = 0;
		float no = 0;
		
		for (Vote vote : votes) {
			switch(VoteChoice.toEnum(vote.getVoteChoice())) {
				case YES:
					yes++;
					break;
				case NO:
					no++;
					break;
				default:
					break;
			}
		}
		
		float totalVotes = yes + no;
		if (yes > no) {
			assembly.setVotesResult(VoteChoice.YES.getId());
			float percentageBasis = yes / totalVotes;
			assembly.setPercentage(percentageBasis * 100);
			return AssemblyStatus.FINISHED;
		} else if (no > yes) {
			assembly.setVotesResult(VoteChoice.NO.getId());
			float percentageBasis = no / totalVotes;
			assembly.setPercentage(percentageBasis * 100);
			return AssemblyStatus.FINISHED;
		} else if (no == yes && totalVotes > 0) {
			assembly.setVotesResult(VoteChoice.NULLIFIED.getId());
			assembly.setPercentage(50.0f);
			return AssemblyStatus.TIED;
		} else {
			assembly.setVotesResult(VoteChoice.NULLIFIED.getId());
			return AssemblyStatus.CANCELED;
		}
	}

}
