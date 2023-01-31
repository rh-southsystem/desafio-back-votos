package br.com.assembliescorp.services.impl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.assembliescorp.domain.dtos.ruling.RulingCreateDTO;
import br.com.assembliescorp.domain.repositories.RulingRepository;

@ExtendWith(MockitoExtension.class)
public class RulingServiceImplTest {
	
	private RulingServiceImpl rulingServiceImpl;
	
	@Mock
	private RulingRepository rulingRepository;
	
	@BeforeEach
    void setUp() {
		rulingServiceImpl = new RulingServiceImpl(rulingRepository);
    }
	
	@Test
    void create() {
        assertThat(rulingServiceImpl.create(buildAssociateCreateDTO())).isNotNull();
    }
    
    @Test
    void getList() {
        assertThat(rulingServiceImpl.getList()).isNotNull();
    }
    
    @Test
    void findOne() {
        assertThat(rulingServiceImpl.findOne(1L)).isNotNull();
    }
       
    
    private RulingCreateDTO buildAssociateCreateDTO() {
    	return RulingCreateDTO.builder().name("Extraordinária").build();
    }

}
