package com.south.voting.domain;

import com.south.voting.domain.TopicEntity;

public class ResultTopic {

    private Long countVotes;
    private Long countVotesYes;
    private Long countVotesNo;
    private TopicEntity topicEntity;

    public Long getCountVotes() {
        return countVotes;
    }

    public void setCountVotes(Long countVotes) {
        this.countVotes = countVotes;
    }

    public Long getCountVotesYes() {
        return countVotesYes;
    }

    public void setCountVotesYes(Long countVotesYes) {
        this.countVotesYes = countVotesYes;
    }

    public Long getCountVotesNo() {
        return countVotesNo;
    }

    public void setCountVotesNo(Long countVotesNo) {
        this.countVotesNo = countVotesNo;
    }

    public TopicEntity getTopicEntity() {
        return topicEntity;
    }

    public void setTopicEntity(TopicEntity topicEntity) {
        this.topicEntity = topicEntity;
    }
}
