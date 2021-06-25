package com.south.voting.resources;

import com.south.voting.dto.VoteDTO;
import com.south.voting.service.VoteService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/votes")
public class VoteResource {

    private final VoteService voteService;

    @Autowired
    public VoteResource(VoteService voteService) {
       this.voteService = voteService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveVote(@RequestBody VoteDTO voteDTO) {
        try {
          return new ResponseEntity(voteService.save(voteDTO),HttpStatus.CREATED);
        } catch (Exception e ) {
          return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
