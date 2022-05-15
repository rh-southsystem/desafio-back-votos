package com.southsystem.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.southsystem.domain.Assembly;
import com.southsystem.domain.Associate;
import com.southsystem.domain.enums.AssemblyStatus;
import com.southsystem.dto.AssemblyCreateDTO;
import com.southsystem.dto.AssemblyReadDTO;
import com.southsystem.dto.AssemblyUpdateDTO;
import com.southsystem.dto.AssociateUpdateDTO;
import com.southsystem.repository.AssemblyRepository;

@ExtendWith(MockitoExtension.class)
public class AssemblyServiceTest {

	@Mock
	private AssemblyRepository assemblyRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private AssemblyService assemblyService;
	
	private Assembly assembly;

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
		assembly = new Assembly(1, "Assembly 1", "Gathering", AssemblyStatus.PENDING.getId(),
				LocalDateTime.now(), null, 100L);
	}
	
	@DisplayName("Validate create method")
    @Test
    public void testCreate() {
    	when(assemblyRepository.save(any(Assembly.class))).thenReturn(assembly);
    	when(modelMapper.map(any(), any())).thenReturn(assembly);
    	
    	AssemblyCreateDTO assemblyCreateDTO = new AssemblyCreateDTO("Assembly 1", "Gathering", 100L);
    	Assembly assembly = assemblyService.create(assemblyCreateDTO);
    	Assertions.assertNotNull(assembly.getId());
    	Assertions.assertNotNull(assembly.getCreationDate());
    }
	
	@DisplayName("Validate findById method")
    @Test
    public void testFindById() {
    	when(assemblyRepository.findById(any(Integer.class))).thenReturn(Optional.of(assembly));
    	
    	final Integer id = 1;
    	Assembly assembly = assemblyService.findById(id);
    	Assertions.assertEquals(assembly.getId(), id);
    }
	
	@DisplayName("Validate list method")
    @Test
    public void testList() {
    	List<Assembly> assemblies = new ArrayList<>();
    	assemblies.add(assembly);
    	when(assemblyRepository.findByTitleContainsIgnoreCase(any(String.class), any(Pageable.class)))
    		.thenReturn(new PageImpl<Assembly>(assemblies));
    	
    	Page<AssemblyReadDTO> assemblyList = assemblyService.list(0, 10, "title", "DESC", "");
    	Assertions.assertFalse(assemblyList.isEmpty());
    }
	
	@DisplayName("Validate update method")
    @Test
    public void testUpdate() {
    	final String updateTitle = "Title 2";
    	final String updateDescription = "Assembly 2";
    	final Long updateDuration = 200L;
    	Assembly assembly = new Assembly(1, updateTitle, updateDescription, AssemblyStatus.PENDING.getId(),
    			null, LocalDateTime.now(), updateDuration);
    	when(assemblyRepository.save(any(Assembly.class))).thenReturn(assembly);
    	when(assemblyRepository.findById(any(Integer.class))).thenReturn(Optional.of(this.assembly));
    	
    	AssemblyUpdateDTO assemblyUpdateDTO = new AssemblyUpdateDTO(1, updateTitle, updateDescription, updateDuration);
    	Assembly updatedAssembly = assemblyService.update(assemblyUpdateDTO);
    	Assertions.assertEquals(updatedAssembly.getTitle(), assembly.getTitle());
    	Assertions.assertEquals(updatedAssembly.getDescription(), assembly.getDescription());
    	Assertions.assertEquals(updatedAssembly.getDuration(), assembly.getDuration());
    	Assertions.assertNotNull(updatedAssembly.getUpdateDate());
    }
	
	 @DisplayName("Validate delete method")
	    @Test
	    public void testDelete() {
	    	when(assemblyRepository.findById(any(Integer.class))).thenReturn(Optional.of(assembly));
	    	
	    	final Integer id = 1;
	    	assemblyService.delete(id);
	    }

}
