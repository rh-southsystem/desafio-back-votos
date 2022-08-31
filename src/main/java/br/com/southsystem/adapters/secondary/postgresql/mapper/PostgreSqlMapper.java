package br.com.southsystem.adapters.secondary.postgresql.mapper;

import br.com.southsystem.adapters.secondary.postgresql.entity.AssociatePost;
import br.com.southsystem.adapters.secondary.postgresql.entity.VoteSessionPost;
import br.com.southsystem.adapters.secondary.postgresql.entity.VotingPost;
import br.com.southsystem.application.domain.entity.Associate;
import br.com.southsystem.application.domain.entity.VoteSession;
import br.com.southsystem.application.domain.entity.Voting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PostgreSqlMapper {
    Associate toAssociateEntity(AssociatePost associatePost);
    VoteSession toVoteSessionEntity(VoteSessionPost voteSessionPost);

    VoteSessionPost toVoteSessionPost(VoteSession voteSession);

    @Mapping(target = "associate.id", source = "associateId")
    @Mapping(target = "voteSession.id", source = "voteSessionId")
    Voting toVotingEntity(VotingPost voting);

    @Mapping(target = "associateId", source = "associate.id")
    @Mapping(target = "voteSessionId", source = "voteSession.id")
    VotingPost toVotingPost(Voting voting);
}
