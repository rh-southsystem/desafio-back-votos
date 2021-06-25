package com.south.voting.util;

import com.south.voting.domain.StatusDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AssociateRestTemplate implements AssociateRest {

    private final Logger LOGGER = LoggerFactory.getLogger(AssociateRestTemplate.class);

    private RestTemplate restTemplate;

    @Value("${uri.api.heroku}")
    private String url;

    public AssociateRestTemplate() {
       this.restTemplate = new RestTemplate();
    }


    @Override
    public StatusDocument validateDocumentAssociate(String document) throws Exception {
        try {
            final String urlFinal = url + document;
            ResponseEntity<StatusDocument> response = restTemplate.getForEntity(urlFinal,StatusDocument.class);
            LOGGER.info("Response status document valid {} , value {} ",response.getStatusCode(),response.getBody());
            return response.getBody();
        } catch (Exception e ) {
            throw new Exception(e);
        }
    }
}
