package com.south.voting.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "TB_SESSION")
public class SessionEntity {

    private Long id;
    private TopicEntity topicEntity;
    private Set<VoteEntity> votes;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expiration;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "TOPIC_ID")
    public TopicEntity getTopic() {
        return topicEntity;
    }

    public void setTopic(TopicEntity topicEntity) {
        this.topicEntity = topicEntity;
    }

    @OneToMany(mappedBy = "session",
               fetch = FetchType.LAZY)
    public Set<VoteEntity> getVotes() {
        return votes;
    }

    public void setVotes(Set<VoteEntity> votes) {
        this.votes = votes;
    }

    @Column(name = "EXPIRATION")
    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }
}
