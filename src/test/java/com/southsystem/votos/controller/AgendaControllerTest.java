package com.southsystem.votos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.southsystem.votos.dto.AgendaDTO;
import com.southsystem.votos.dto.VoteDTO;
import com.southsystem.votos.dto.VotingResultDTO;
import com.southsystem.votos.entity.Agenda;
import com.southsystem.votos.producer.SQSMessageProducer;
import com.southsystem.votos.service.AgendaService;
import com.southsystem.votos.service.VotingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(AgendaController.class)
public class AgendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgendaService agendaService;

    @MockBean
    private VotingService votingService;

    @MockBean
    private SQSMessageProducer producer;

    private static final String BASE_URL = "http://localhost:8081/v1/agenda";

    @Test
    public void openVoting_ValidParams_returnOk() throws Exception {
        doNothing().when(agendaService).openSession(anyLong(), anyInt());

        mockMvc.perform(MockMvcRequestBuilders
                        .put(BASE_URL + "/1?time=60")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(agendaService, times(1)).openSession(anyLong(), anyInt());
    }

    @Test
    public void createAgenda_ValidDTO_returnCreated() throws Exception {
        Mockito.when(agendaService.saveAgenda(any(Agenda.class))).thenReturn(any(Agenda.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL)
                        .content(new ObjectMapper().writeValueAsBytes(AgendaDTO.builder().description("Pauta 1").build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        verify(agendaService, times(1)).saveAgenda(any(Agenda.class));
    }

    @Test
    public void sendVote_ValidDto_returnOk() throws Exception {
        doNothing().when(votingService).sendVote(any(VoteDTO.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_URL + "/vote")
                        .content(new ObjectMapper().writeValueAsBytes(VoteDTO.builder().build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(votingService, times(1)).sendVote(any(VoteDTO.class));

    }

    @Test
    public void getVotingResult_ValidParams_returnOk() throws Exception {
        Mockito.when(votingService.getVotingResult(anyLong())).thenReturn(
                VotingResultDTO.builder().agendaId(1L).countVotesNo(10l).countVotesYes(15l).build());

        mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_URL + "/1/result")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"agendaId\":1,\"countVotesYes\":15,\"countVotesNo\":10}"));;

        verify(votingService, times(1)).getVotingResult(anyLong());
    }

}
