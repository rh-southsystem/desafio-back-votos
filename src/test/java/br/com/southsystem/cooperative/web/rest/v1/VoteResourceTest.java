package br.com.southsystem.cooperative.web.rest.v1;

import br.com.southsystem.cooperative.service.VoteService;
import br.com.southsystem.cooperative.service.dto.VoteCreateRequestDTO;
import br.com.southsystem.cooperative.service.dto.VoteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class VoteResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VoteService votoService;

    @Test
    void voteSuccessfullyTakenTest() throws Exception {
        var cpf = "26283451020";
        var vote = VoteCreateRequestDTO.builder()
                .affiliatedCpf(cpf)
                .sessionId(1L)
                .build();
        var voteDto = VoteDTO.builder()
                .affiliatedCpf(cpf)
                .sessionId(1L)
                .affiliatedId(1L)
                .build();

        when(votoService.vote(any(VoteCreateRequestDTO.class))).thenReturn(voteDto);

        mockMvc.perform(post("/v1/votes")
                .content(objectMapper.writeValueAsString(vote))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }




}
