package br.com.southsystem.cooperative.service.mapper;

import br.com.southsystem.cooperative.domain.Session;
import br.com.southsystem.cooperative.service.dto.SessionDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {SubjectMapper.class})
public interface SessionMapper extends EntityMapper<SessionDTO, Session> {

}
