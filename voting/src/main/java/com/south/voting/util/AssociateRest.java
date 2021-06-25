package com.south.voting.util;

import com.south.voting.domain.StatusDocument;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

public interface AssociateRest {

    @Retryable(value = Exception.class,maxAttempts = 2,backoff = @Backoff(delay = 1000))
    StatusDocument validateDocumentAssociate(String document) throws Exception;
}
