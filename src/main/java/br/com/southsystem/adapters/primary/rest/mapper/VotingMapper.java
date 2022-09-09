package br.com.southsystem.adapters.primary.rest.mapper;

import br.com.southsystem.adapters.primary.rest.dto.VotingRequest;
import br.com.southsystem.adapters.primary.rest.dto.VotingResponse;
import br.com.southsystem.application.domain.entity.Voting;
import br.com.southsystem.application.domain.entity.enums.VoteType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface VotingMapper {
    VotingResponse toResponse(Voting voting);

    @Mapping(target = "voteType", source = "voteType")
    @Mapping(target = "associate.id", source = "associateId")
    @Mapping(target = "voteSession.id", source = "voteSessionId")
    Voting toEntity(VotingRequest votingRequest);

    default VoteType map(String voteType) {
        return VoteType.valueOf(voteType);
    }
}
