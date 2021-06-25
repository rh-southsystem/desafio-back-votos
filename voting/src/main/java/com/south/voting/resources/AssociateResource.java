package com.south.voting.resources;

import com.south.voting.domain.AssociateEntity;
import com.south.voting.service.AssociateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/associates")
@CrossOrigin
public class AssociateResource {

    private final Logger LOG = LoggerFactory.getLogger(AssociateResource.class);

    private final AssociateService associateService;

    @Autowired
    public AssociateResource(AssociateService associateService) {
       this.associateService = associateService;
    }

    @ResponseBody
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveAssociate(@RequestBody AssociateEntity associateEntity) {
       try {
          return new ResponseEntity(associateService.save(associateEntity),HttpStatus.CREATED);
       } catch (Exception e) {
          return new ResponseEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
       }
    }


}
