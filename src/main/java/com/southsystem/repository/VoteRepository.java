package com.southsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.southsystem.domain.Vote;
import com.southsystem.domain.VotePK;

public interface VoteRepository extends JpaRepository<Vote, VotePK> {

}
