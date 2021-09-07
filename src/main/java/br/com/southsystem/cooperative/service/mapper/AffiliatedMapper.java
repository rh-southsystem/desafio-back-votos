package br.com.southsystem.cooperative.service.mapper;

import br.com.southsystem.cooperative.domain.Affiliated;
import br.com.southsystem.cooperative.service.dto.AffiliatedDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AffiliatedMapper extends EntityMapper<AffiliatedDTO, Affiliated> {
}
