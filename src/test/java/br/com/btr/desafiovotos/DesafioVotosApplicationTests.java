package br.com.btr.desafiovotos;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class DesafioVotosApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Test
	void testGetPautaResultIdInvalido() throws Exception {
		mvc.perform(get("/pauta/v1/1000/result").contentType(MediaType.APPLICATION_JSON)).andExpect(status().is4xxClientError());
	}
}
