package com.south.voting;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.south.voting.domain.AssociateEntity;
import com.south.voting.dto.AssociateDTO;
import com.south.voting.resources.AssociateResource;
import com.south.voting.service.AssociateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(AssociateResource.class)
public class AssociateResourceTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AssociateService associateService;

    @Test
    public void includeAssociateOK() throws Exception {
        AssociateDTO associateDTO = new AssociateDTO();
        associateDTO.setName("teste 12");
        associateDTO.setDocument("52010761065");

        AssociateEntity associateEntity = new AssociateEntity();
        associateEntity.setId(Long.valueOf(1));
        associateEntity.setDocument(associateDTO.getDocument());
        associateEntity.setName(associateDTO.getName());

        when(associateService.save(any(AssociateEntity.class)))
            .thenReturn(associateEntity);

        String json = new ObjectMapper().writeValueAsString(associateDTO);

        mvc.perform(post("/associates")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(json)
                .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(associateEntity.getId()))
                .andExpect(jsonPath("name").value(associateEntity.getName()))
                .andExpect(jsonPath("document").value(associateEntity.getDocument()));
    }







}
