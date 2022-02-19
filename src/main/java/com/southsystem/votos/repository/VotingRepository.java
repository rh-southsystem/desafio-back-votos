package com.southsystem.votos.repository;

import com.southsystem.votos.entity.Voting;
import com.southsystem.votos.enums.VoteTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VotingRepository extends JpaRepository<Voting, Long> {

    @Query(value = "SELECT COUNT(v.id) FROM voting v WHERE v.vote_type = :voteType AND v.agenda_id = :agendaId",
            nativeQuery = true)
    Long countVotesByVoteTypeAndAgendaId(String voteType, Long agendaId);



}
