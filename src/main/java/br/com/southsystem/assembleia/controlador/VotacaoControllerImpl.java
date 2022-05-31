package br.com.southsystem.assembleia.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.southsystem.assembleia.dto.VotacaoDTO;
import br.com.southsystem.assembleia.dto.VotoDTO;
import br.com.southsystem.assembleia.entidade.Votacao;
import br.com.southsystem.assembleia.entidade.enums.VotoEnum;
import br.com.southsystem.assembleia.mapper.VotacaoMapper;
import br.com.southsystem.assembleia.servico.VotacaoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/v1/votacao", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class VotacaoControllerImpl implements VotacaoController {

    private final VotacaoService votacaoService;
    
    private final VotacaoMapper votacaoMapper;

    @PostMapping
	public ResponseEntity<Void> votar(VotoDTO votoDTO) {
		Optional<Votacao> votacaoOptional = this.votacaoService.votar(this.votacaoMapper.toVotacao(votoDTO));
    	if (votacaoOptional.isPresent()) {
    		return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().queryParam("idPauta", votoDTO.getIdPauta()).queryParam("idAssociado", votoDTO.getIdAssociado()).queryParam("voto", votoDTO.getVoto()).build().toUri()).build();
		} else {
			return ResponseEntity.unprocessableEntity().build();
		}
	}

    @GetMapping
	public ResponseEntity<List<VotacaoDTO>> consultar(Long idPauta, Optional<Long> idAssociado,
			Optional<VotoEnum> voto) {
		Optional<List<Votacao>> pautaOptionalList = this.votacaoService.consultar(idPauta, idAssociado, voto);
    	if (pautaOptionalList.isPresent()) {
    		return ResponseEntity.ok(this.votacaoMapper.toVotacaoDtoList(pautaOptionalList.get()));		
    	} else {
    		return ResponseEntity.noContent().build();
    	}
	}

}