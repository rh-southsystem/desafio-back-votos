package com.south.voting.service;

import com.south.voting.domain.VoteEntity;
import com.south.voting.dto.VoteDTO;

public interface VoteService {
    VoteEntity save(VoteDTO voteDTO) throws Exception;
}
