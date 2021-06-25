package com.south.voting.resources;

import com.south.voting.dto.SessionDTO;
import com.south.voting.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sessions")
public class SessionResource {

    private final SessionService sessionService;

    @Autowired
    public SessionResource(SessionService sessionService) {
       this.sessionService = sessionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveSession(@RequestBody SessionDTO sessionDTO) {
        try {
            return new ResponseEntity(sessionService.save(sessionDTO),HttpStatus.CREATED);
        } catch (Exception e) {
           return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
