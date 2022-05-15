package com.southsystem.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.southsystem.domain.Assembly;
import com.southsystem.domain.enums.AssemblyStatus;
import com.southsystem.dto.AssemblyCreateDTO;
import com.southsystem.dto.AssemblyReadDTO;
import com.southsystem.dto.AssemblyUpdateDTO;
import com.southsystem.repository.AssemblyRepository;
import com.southsystem.service.exception.CannotUpdateAssemblyException;
import com.southsystem.service.exception.EntityNotFoundException;
import com.southsystem.service.exception.AssemblyNotStartedException;
import com.southsystem.service.exception.InvalidAssemblyToStartVotingException;

@Service
public class AssemblyService {
	
	@Autowired
	private AssemblyRepository assemblyRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public Assembly create(AssemblyCreateDTO assemblyCreateDTO) {
		Assembly assembly = modelMapper.map(assemblyCreateDTO, Assembly.class);
		assembly.setCreationDate(LocalDateTime.now());
		assembly.setStatus(AssemblyStatus.PENDING.getId());
		return assemblyRepository.save(assembly);
	}
	
	public Assembly findById(Integer id) {
		Optional<Assembly> optionalAssembly = assemblyRepository.findById(id);
		if (optionalAssembly.isEmpty()) {
			throw new EntityNotFoundException();
		}
		
		return optionalAssembly.get();
	}
	
	public Page<AssemblyReadDTO> list(Integer page, Integer linesPerPage, String orderBy,
			String direction, String title) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,
				Direction.valueOf(direction), orderBy);
		
		return assemblyRepository.findByTitleContainsIgnoreCase(title, pageRequest)
				.map(associate -> modelMapper.map(associate, AssemblyReadDTO.class));
	}
	
	public Page<Assembly> listByStatus(Integer page, Integer linesPerPage, String orderBy,
			String direction, AssemblyStatus status) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage,
				Direction.valueOf(direction), orderBy);
		
		return assemblyRepository.findByStatus(status.getId(), pageRequest);
	}
	
	public Assembly update(AssemblyUpdateDTO assemblyUpdateDTO) {
		Assembly assembly = findById(assemblyUpdateDTO.getId());
		
		if (assembly.getStatus() != AssemblyStatus.PENDING.getId()) {
			throw new CannotUpdateAssemblyException();
		}
		
		assembly.setTitle(assemblyUpdateDTO.getTitle());
		assembly.setDescription(assemblyUpdateDTO.getDescription());
		assembly.setDuration(assemblyUpdateDTO.getDuration());
		assembly.setUpdateDate(LocalDateTime.now());
		return assemblyRepository.save(assembly);
	}
	
	public void delete(Integer id) {
		findById(id);
		assemblyRepository.deleteById(id);
	}
	
	public Assembly startVoting(Integer id) {
		Assembly assembly = findById(id);
		if (assembly.getStatus() != AssemblyStatus.PENDING.getId()) {
			throw new InvalidAssemblyToStartVotingException();
		}
		
		assembly.setStatus(AssemblyStatus.STARTED.getId());
		assembly.setStartDate(LocalDateTime.now());
		assembly.setUpdateDate(LocalDateTime.now());
		
		return assemblyRepository.save(assembly);
	}
	
	public Assembly finishVoting(Assembly assembly) {
		if (assembly.getStatus() != AssemblyStatus.STARTED.getId()) {
			throw new AssemblyNotStartedException();
		}
		
		assembly.setStatus(AssemblyStatus.FINISHED.getId());
		assembly.setFinishDate(LocalDateTime.now());
		assembly.setUpdateDate(LocalDateTime.now());
		
		return assemblyRepository.save(assembly);
	}

}
