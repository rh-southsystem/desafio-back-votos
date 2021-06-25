package com.south.voting.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.south.voting.domain.SessionEntity;

import java.time.LocalDateTime;
import java.util.Objects;

public class SessionDTO {

    private Long idTopic;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiration;

    public Long getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(Long idTopic) {
        this.idTopic = idTopic;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public static SessionEntity fromToSessionEntity(SessionDTO sessionDTO){
        SessionEntity sessionEntity = new SessionEntity();
        LocalDateTime expiration = Objects.nonNull(sessionDTO.getExpiration()) ?
                                   sessionDTO.getExpiration() :
                                   LocalDateTime.now().plusMinutes(1);
        sessionEntity.setExpiration(expiration);
        return sessionEntity;
    }
}
