package br.com.southsystem.cooperative.web.rest.v1;

import br.com.southsystem.cooperative.service.SubjectService;
import br.com.southsystem.cooperative.service.dto.SubjectCreateRequestDTO;
import br.com.southsystem.cooperative.service.dto.SubjectDTO;
import br.com.southsystem.cooperative.service.dto.SubjectResultDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
public class SubjectResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SubjectService subjectService;

    @Test
    @DisplayName("Request to save a subject")
    void saveASubjectTest() throws Exception {

        var subjectCreateRequestDTO = SubjectCreateRequestDTO.builder()
                .subject("One subject")
                .build();
        var voteDto = SubjectDTO.builder()
                .subject("One subject")
                .id(1L)
                .build();

        when(subjectService.save(any(SubjectCreateRequestDTO.class))).thenReturn(voteDto);

        mockMvc.perform(post("/v1/subjects")
                .content(objectMapper.writeValueAsString(subjectCreateRequestDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Request to get a subject by id")
    void getSubjectTest() throws Exception {

        var subjectDTO = SubjectDTO.builder()
                .subject("One subject")
                .id(21L)
                .build();
        Optional<SubjectDTO> optional = Optional.of(subjectDTO);
        when(subjectService.findOne(21L)).thenReturn(optional);
        var responseResult = mockMvc.perform(get("/v1/subjects/{id}", 21L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
        String jsonString = responseResult.getResponse().getContentAsString();
        SubjectDTO anotherSubjectDTO = objectMapper.readValue(jsonString, SubjectDTO.class);
        assertNotNull(anotherSubjectDTO.getSubject());
        assertEquals("One subject", anotherSubjectDTO.getSubject());
        assertNotNull(anotherSubjectDTO.getId());
        assertEquals(21L, anotherSubjectDTO.getId());
    }

    @Test
    @DisplayName("Request to get Subject with voting result by id")
    void getSubjectWithVotingResultTest() throws Exception {

        var subjectResultDTO = SubjectResultDTO.builder()
                .subject("One subject")
                .id(21L)
                .noVotes(10)
                .yesVotes(18)
                .build();

        when(subjectService.votingResult(21L)).thenReturn(subjectResultDTO);
        var responseResult = mockMvc.perform(get("/v1/subjects/getSubjectWithVotingResult/{id}", 21L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andReturn();
        String jsonString = responseResult.getResponse().getContentAsString();
        SubjectResultDTO anotherSubjectResultDTO = objectMapper.readValue(jsonString, SubjectResultDTO.class);
        assertNotNull(anotherSubjectResultDTO.getSubject());
        assertEquals("One subject", anotherSubjectResultDTO.getSubject());
        assertNotNull(anotherSubjectResultDTO.getId());
        assertEquals(21L, anotherSubjectResultDTO.getId());
        assertNotNull(anotherSubjectResultDTO.getYesVotes());
        assertEquals(18, anotherSubjectResultDTO.getYesVotes());
        assertNotNull(anotherSubjectResultDTO.getNoVotes());
        assertEquals(10, anotherSubjectResultDTO.getNoVotes());
    }
}
