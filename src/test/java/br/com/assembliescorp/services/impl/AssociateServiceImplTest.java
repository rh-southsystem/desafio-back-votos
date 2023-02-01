package br.com.assembliescorp.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.assembliescorp.domain.dtos.associate.AssociateCreateDTO;
import br.com.assembliescorp.domain.repositories.AssociateRepository;

@ExtendWith(MockitoExtension.class)
public class AssociateServiceImplTest {
	
    private AssociateServiceImpl associateServiceImpl;
	
	@Mock
	private AssociateRepository associateRepository;

    @BeforeEach
    void setUp() {
    	associateServiceImpl = new AssociateServiceImpl(associateRepository);
    }

    @Test
    void create() {
        assertThat(associateServiceImpl.create(buildAssociateCreateDTO())).isNotNull();
    }
    
    @Test
    void getList() {
        assertThat(associateServiceImpl.getList()).isNotNull();
    }
    
    @Test
    void findOne() {
        assertThat(associateServiceImpl.findOne(1L)).isNotNull();
    }
       
    
    private AssociateCreateDTO buildAssociateCreateDTO() {
    	return AssociateCreateDTO.builder().name("Alan").cpf("1234").build();
    }
    
    
}
