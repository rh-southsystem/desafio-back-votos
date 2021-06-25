package com.south.voting.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.south.voting.enums.VoteEnum;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@Entity
@Table(name = "TB_VOTE")
public class VoteEntity {

    private Long id;
    private VoteEnum voteEnum;
    private SessionEntity sessionEntity;
    private AssociateEntity associateEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "VALUE")
    @Enumerated(EnumType.STRING)
    public VoteEnum getVoteEnum() {
        return voteEnum;
    }

    public void setVoteEnum(VoteEnum voteEnum) {
        this.voteEnum = voteEnum;
    }

    @ManyToOne
    @JoinColumn(name = "ID_SESSION")
    @JsonIgnore
    public SessionEntity getSession() {
        return sessionEntity;
    }

    public void setSession(SessionEntity sessionEntity) {
        this.sessionEntity = sessionEntity;
    }

    @OneToOne
    @JoinColumn(name = "ID_ASSOCIATE")
    @JsonIgnore
    public AssociateEntity getAssociate() {
        return associateEntity;
    }

    public void setAssociate(AssociateEntity associateEntity) {
        this.associateEntity = associateEntity;
    }


}
