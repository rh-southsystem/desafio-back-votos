package com.south.voting.dto;

import com.south.voting.enums.VoteEnum;

public class VoteDTO {

    private VoteEnum voteEnum;
    private Long idSession;
    private Long idAssociate;

    public VoteEnum getVoteEnum() {
        return voteEnum;
    }

    public void setVoteEnum(VoteEnum voteEnum) {
        this.voteEnum = voteEnum;
    }

    public Long getIdSession() {
        return idSession;
    }

    public void setIdSession(Long idSession) {
        this.idSession = idSession;
    }

    public Long getIdAssociate() {
        return idAssociate;
    }

    public void setIdAssociate(Long idAssociate) {
        this.idAssociate = idAssociate;
    }
}
