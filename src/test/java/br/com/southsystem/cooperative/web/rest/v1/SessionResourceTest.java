package br.com.southsystem.cooperative.web.rest.v1;


import br.com.southsystem.cooperative.service.SessionService;

import br.com.southsystem.cooperative.service.dto.SessionDTO;

import br.com.southsystem.cooperative.service.dto.SessionInitRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SessionResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SessionService sessionService;

    @Test
    @DisplayName("Request to init a session")
    void initASessionTest() throws Exception {
        var endDateTime = LocalDateTime.now().plusMinutes(60);
        var sessionInitRequestDTO = SessionInitRequestDTO.builder()
                .subjectId(1L)
                .endDateTime(endDateTime)
                .build();
        var sessionDTO = SessionDTO.builder()
                .subjectId(1L)
                .endDateTime(endDateTime)
                .id(21L)
                .build();

        when(sessionService.init(any(SessionInitRequestDTO.class))).thenReturn(sessionDTO);

        var responseResult = mockMvc.perform(post("/v1/sessions")
                .content(objectMapper.writeValueAsString(sessionInitRequestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        String jsonString = responseResult.getResponse().getContentAsString();
        SessionDTO anotherSessionDTO = objectMapper.readValue(jsonString, SessionDTO.class);
        assertNotNull(anotherSessionDTO.getSubjectId());
        assertEquals(1L, anotherSessionDTO.getSubjectId());
        assertNotNull(anotherSessionDTO.getId());
        assertEquals(21L, anotherSessionDTO.getId());
    }

    @Test
    @DisplayName("Request to get a session by id")
    void getSessionTest() throws Exception {

        var sessionDTO = SessionDTO.builder()
                .subjectId(1L)
                .id(21L)
                .build();
        Optional<SessionDTO> optional = Optional.of(sessionDTO);
        when(sessionService.findOne(21L)).thenReturn(optional);
        var responseResult = mockMvc.perform(get("/v1/sessions/{id}", 21L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
        String jsonString = responseResult.getResponse().getContentAsString();
        SessionDTO anotherSessionDTO = objectMapper.readValue(jsonString, SessionDTO.class);
        assertNotNull(anotherSessionDTO.getSubjectId());
        assertEquals(1L, anotherSessionDTO.getSubjectId());
        assertNotNull(anotherSessionDTO.getId());
        assertEquals(21L, anotherSessionDTO.getId());
    }
}
