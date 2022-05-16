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
import org.springframework.http.HttpStatus;

import com.southsystem.domain.Associate;
import com.southsystem.domain.enums.AssociatePermission;
import com.southsystem.dto.AssociateCreateDTO;
import com.southsystem.dto.AssociateReadDTO;
import com.southsystem.dto.AssociateUpdateDTO;
import com.southsystem.repository.AssociateRepository;

@ExtendWith(MockitoExtension.class)
public class AssociateServiceTest {
	
	@Mock
    private AssociateRepository associateRepository;
	
	@Mock
	private CPFValidatorService cpfValidatorService;
	
	@Mock
    private ModelMapper modelMapper;
	
	@InjectMocks
    private AssociateService associateService;
	
	private Associate associate;
    
    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        associate = new Associate(1, "92836845082", "João", AssociatePermission.ABLE_TO_VOTE.getId(),
        		LocalDateTime.now(), null);
    }
    
    @DisplayName("Validate create method")
    @Test
    public void testCreate() {
    	when(associateRepository.save(any(Associate.class))).thenReturn(associate);
    	when(cpfValidatorService.callApiValidation(any(String.class))).thenReturn(HttpStatus.OK.value());
    	when(modelMapper.map(any(), any())).thenReturn(associate);
    	
    	AssociateCreateDTO associateCreateDTO = new AssociateCreateDTO("92836845082", "João");
    	Associate associate = associateService.create(associateCreateDTO);
    	Assertions.assertNotNull(associate.getId());
    	Assertions.assertNotNull(associate.getCreationDate());
    }
    
    @DisplayName("Validate findById method")
    @Test
    public void testFindById() {
    	when(associateRepository.findById(any(Integer.class))).thenReturn(Optional.of(associate));
    	
    	final Integer id = 1;
    	Associate associate = associateService.findById(id);
    	Assertions.assertEquals(associate.getId(), id);
    }
    
    @DisplayName("Validate list method")
    @Test
    public void testList() {
    	List<Associate> associates = new ArrayList<>();
        associates.add(associate);
    	when(associateRepository.findByNameContainsIgnoreCase(any(String.class), any(Pageable.class)))
    		.thenReturn(new PageImpl<Associate>(associates));
    	
    	Page<AssociateReadDTO> associateList = associateService.list(0, 10, "title", "DESC", "");
    	Assertions.assertFalse(associateList.isEmpty());
    }
    
    @DisplayName("Validate update method")
    @Test
    public void testUpdate() {
    	final String updateCPF = "04945997071";
    	final String updateName = "Gustavo";
    	Associate associate = new Associate(1, updateCPF, updateName, AssociatePermission.ABLE_TO_VOTE.getId(),
    			null, LocalDateTime.now());
    	when(associateRepository.save(any(Associate.class))).thenReturn(associate);
    	when(associateRepository.findById(any(Integer.class))).thenReturn(Optional.of(this.associate));
    	when(cpfValidatorService.callApiValidation(any(String.class))).thenReturn(HttpStatus.OK.value());
    	
    	AssociateUpdateDTO associateUpdateDTO = new AssociateUpdateDTO(1, updateCPF, updateName);
    	Associate updatedAssociate = associateService.update(associateUpdateDTO);
    	Assertions.assertEquals(updatedAssociate.getCpf(), associate.getCpf());
    	Assertions.assertEquals(updatedAssociate.getName(), associate.getName());
    	Assertions.assertNotNull(updatedAssociate.getUpdateDate());
    }
    
    @DisplayName("Validate delete method")
    @Test
    public void testDelete() {
    	when(associateRepository.findById(any(Integer.class))).thenReturn(Optional.of(associate));
    	
    	final Integer id = 1;
    	associateService.delete(id);
    }

}
