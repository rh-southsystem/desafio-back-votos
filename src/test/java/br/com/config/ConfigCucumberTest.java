package br.com.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import br.com.app.APIPautaEVotacao;
import io.cucumber.java.Before;
import io.cucumber.spring.CucumberContextConfiguration;


@ContextConfiguration(loader = SpringBootContextLoader.class)
@CucumberContextConfiguration

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {APIPautaEVotacao.class })
public class ConfigCucumberTest {

	  private static final Logger LOG = LoggerFactory.getLogger(ConfigCucumberTest.class);

	
	 @Before
	  public void setUp() {
	    LOG.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
	  }
	
}
