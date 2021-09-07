package br.com.southsystem.cooperative.service.mapper;

import br.com.southsystem.cooperative.domain.Session;
import br.com.southsystem.cooperative.service.dto.SessionDTO;
import br.com.southsystem.cooperative.service.dto.SessionInitRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {SubjectMapper.class})
public interface SessionMapper extends EntityMapper<SessionDTO, Session> {

    @Mapping(source = "subject.subject", target = "subjectSubject")
    SessionDTO toDto(Session session);


    @Mapping(source = "subjectId", target = "subjectId")
    @Mapping(source = "endDateTime", target = "endDateTime")
    SessionDTO toSessionInitRequestDto(SessionInitRequestDTO initRequestDTO);

    @Mapping(source = "subjectId", target = "subject.id")
    Session toEntity(SessionDTO sessionDTO);

}
