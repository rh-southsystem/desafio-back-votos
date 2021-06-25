package com.south.voting.resources;

import com.south.voting.domain.TopicEntity;
import com.south.voting.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/topics")
public class TopicResource {

    private final TopicService topicService;

    @Autowired
    public TopicResource(TopicService topicService) {
       this.topicService = topicService;
    }
    
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveTopic(@RequestBody TopicEntity topicEntity){
        try {
           return new ResponseEntity(topicService.save(topicEntity),HttpStatus.CREATED);
        } catch (Exception e) {
          return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}/ascertainment")
    public ResponseEntity generateResultTopic(@PathVariable("id") Long id) {
       try {
          return new ResponseEntity(topicService.getResultTopic(id),HttpStatus.OK);
       } catch (Exception e) {
          return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
    }
    
}
