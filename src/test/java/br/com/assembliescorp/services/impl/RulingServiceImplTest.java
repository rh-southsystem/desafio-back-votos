package br.com.assembliescorp.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}
