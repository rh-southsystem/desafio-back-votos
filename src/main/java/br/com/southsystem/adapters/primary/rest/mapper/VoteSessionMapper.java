package br.com.southsystem.adapters.primary.rest.mapper;

import br.com.southsystem.adapters.primary.rest.dto.VoteSessionRequest;
import br.com.southsystem.adapters.primary.rest.dto.VoteSessionResponse;
import br.com.southsystem.adapters.primary.rest.dto.VoteSessionResultResponse;
import br.com.southsystem.application.domain.entity.VoteSession;
import org.mapstruct.Mapper;

@Mapper
public interface VoteSessionMapper {
    VoteSessionResponse toDTO(VoteSession voteSession);

    VoteSessionResultResponse toResultDTO(VoteSession voteSession);
    VoteSession toEntity(VoteSessionRequest requestDTO);

}
