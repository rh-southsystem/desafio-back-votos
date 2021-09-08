package br.com.southsystem.cooperative.service.mapper;

import br.com.southsystem.cooperative.domain.Vote;
import br.com.southsystem.cooperative.service.dto.VoteCreateRequestDTO;
import br.com.southsystem.cooperative.service.dto.VoteDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SessionMapper.class, AffiliatedMapper.class})
public interface VoteMapper extends EntityMapper<VoteDTO, Vote> {

    @Mapping(source = "session.id", target = "sessionId")
    @Mapping(source = "session.startDateTime", target = "sessionStartDateTime")
    @Mapping(source = "session.endDateTime", target = "sessionEndDateTime")
    @Mapping(source = "affiliated.id", target = "affiliatedId")
    @Mapping(source = "affiliated.cpf", target = "affiliatedCpf")
    VoteDTO toDto(Vote vote);

    @Mapping(source = "sessionId", target = "sessionId")
    @Mapping(source = "affiliatedCpf", target = "affiliatedCpf")
    @Mapping(source = "vote", target = "vote")
    VoteDTO toDto(VoteCreateRequestDTO voteDTO);

    @Mapping(source = "sessionId", target = "session.id")
    @Mapping(source = "affiliatedId", target = "affiliated.id")
    @Mapping(source = "affiliatedCpf", target = "affiliated.cpf")
    Vote toEntity(VoteDTO voteDTO);

    Vote toEntity(VoteCreateRequestDTO voteDTO);
}
