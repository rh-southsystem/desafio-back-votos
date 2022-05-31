package br.com.southsystem.assembleia.mapper;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import br.com.southsystem.assembleia.dto.VotacaoDTO;
import br.com.southsystem.assembleia.dto.VotoDTO;
import br.com.southsystem.assembleia.entidade.Votacao;

@Mapper(componentModel = "spring")
public interface VotacaoMapper {

	VotacaoMapper INSTANCE = Mappers.getMapper(VotacaoMapper.class);

	@Mapping(source = "idAssociado", target = "votoId.associado.id")
	@Mapping(source = "idPauta", target = "votoId.pauta.id")
	Votacao toVotacao(VotoDTO votoDTO);
	
	@Mapping(source = "votoId.associado.nome", target = "associado.nome")
	@Mapping(source = "votoId.associado.cpf", target = "associado.cpf")
	@Mapping(source = "votoId.pauta.descricao", target = "pauta.descricao")
	@Mapping(source = "votoId.pauta.dtInicio", target = "pauta.dtInicio")
	@Mapping(source = "votoId.pauta.dtFim", target = "pauta.dtFim")
	VotacaoDTO toVotacaoDto(Votacao votacao);

	@IterableMapping(elementTargetType = VotacaoDTO.class)
	List<VotacaoDTO> toVotacaoDtoList(List<Votacao> votacoes);
	
}