package br.com.southsystem.assembleia.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.southsystem.assembleia.dto.AssociadoBasicoDTO;
import br.com.southsystem.assembleia.dto.AssociadoCompletoDTO;
import br.com.southsystem.assembleia.entidade.Associado;
 
@Mapper(componentModel = "spring")
public interface AssociadoMapper {
 
    AssociadoMapper INSTANCE = Mappers.getMapper(AssociadoMapper.class);
 
    Associado toAssociado(AssociadoBasicoDTO associadoBasicoDTO);

    List<AssociadoCompletoDTO> toAssociadoCompletoDtoList(List<Associado> associados);
    
    AssociadoCompletoDTO toAssociadoCompletoDto(Associado associado);

    
}