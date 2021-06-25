package com.south.voting.domain;

import com.south.voting.enums.StatusEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class StatusDocument {

    private StatusEnum status;

    @Enumerated(EnumType.STRING)
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return " StatusDocument { " +
                " status = " + status +
                '}';
    }
}
