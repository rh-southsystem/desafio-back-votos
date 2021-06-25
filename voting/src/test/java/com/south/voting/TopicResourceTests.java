package com.south.voting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.south.voting.domain.TopicEntity;
import com.south.voting.dto.TopicDTO;
import com.south.voting.resources.TopicResource;
import com.south.voting.service.TopicService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(TopicResource.class)
public class TopicResourceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TopicService topicService;

    @Test
    public void insertTopicOK() throws Exception {
        TopicDTO topicDTO = new TopicDTO();
        topicDTO.setName("teste");
        topicDTO.setDescription("description teste");

        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setId(Long.valueOf(1));
        topicEntity.setName(topicDTO.getName());
        topicEntity.setDescription(topicDTO.getDescription());

        when(topicService.save(any(TopicEntity.class)))
                .thenReturn(topicEntity);

        String json = new ObjectMapper().writeValueAsString(topicDTO);

        mockMvc.perform(post("/topics")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(json)
                        .characterEncoding("utf-8"))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("id").value(topicEntity.getId()))
                        .andExpect(jsonPath("name").value(topicEntity.getName()))
                        .andExpect(jsonPath("description").value(topicEntity.getDescription()));

    }

}
