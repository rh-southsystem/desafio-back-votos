package br.com.southsystem.assembleia.mapper;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.southsystem.assembleia.dto.PautaBasicoDTO;
import br.com.southsystem.assembleia.dto.PautaCompletoDTO;
import br.com.southsystem.assembleia.entidade.Pauta;
 
@Mapper(componentModel = "spring")
public interface PautaMapper {
 
    PautaMapper INSTANCE = Mappers.getMapper(PautaMapper.class);
 
    Pauta toPauta(PautaBasicoDTO pautaDto);

    List<PautaCompletoDTO> toPautaCompletoDtoList(List<Pauta> pautas);
    
    PautaCompletoDTO toPautaCompletoDto(Pauta pauta);
    
}