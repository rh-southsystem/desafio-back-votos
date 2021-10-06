package br.com.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.entity.Pauta;
import br.com.service.PautaService;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Quando;

@DataJpaTest
public class PautaTest {
	
	 private static final Logger LOG = LoggerFactory.getLogger(PautaTest.class);
	 
	 @Autowired
	 PautaService pautaService;
	 
	
	@Quando("Crio uma Pauta")
	public void CriandoPauta() throws Throwable{

		Pauta pauta = new Pauta();
		pauta.setNome("teste");
		pautaService.save(pauta);
		assertEquals(1L,pauta.getId().longValue());
	    
	}
	
 

	@E("quando pesquiso a pauta criada")
	public void pesquisandoPauta() throws Throwable{
		   
		Optional<Pauta> pauta2 = pautaService.findById(1L);
		assertTrue(pauta2.isPresent());
	    
	}
	
	
	 
	
	

}
