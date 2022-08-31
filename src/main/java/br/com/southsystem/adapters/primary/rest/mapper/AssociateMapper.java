package br.com.southsystem.adapters.primary.rest.mapper;

import br.com.southsystem.adapters.primary.rest.dto.AssociateResponse;
import br.com.southsystem.application.domain.entity.Associate;
import org.mapstruct.Mapper;

@Mapper
public interface AssociateMapper {
    AssociateResponse toResponse(Associate associatePost);
}
