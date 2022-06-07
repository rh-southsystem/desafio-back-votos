package br.com.southsystem.assembleia.scheduler;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.southsystem.assembleia.dto.VotacaoResultadoDTO;
import br.com.southsystem.assembleia.dto.enums.VotoEnumDTO;
import br.com.southsystem.assembleia.entidade.Pauta;
import br.com.southsystem.assembleia.entidade.Votacao;
import br.com.southsystem.assembleia.entidade.enums.VotoEnum;
import br.com.southsystem.assembleia.mapper.AssociadoMapper;
import br.com.southsystem.assembleia.mapper.PautaMapper;
import br.com.southsystem.assembleia.servico.PautaService;
import br.com.southsystem.assembleia.servico.VotacaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class VotacaoScheduler {

    private static final String UM_MINUTO = "60000";
    
    private static final String QUEUE = "assembleia-sqs";

    private final QueueMessagingTemplate queueMessagingTemplate;
    
    private final VotacaoService votacaoService;
    
    private final PautaService pautaService;
    
    private final PautaMapper pautaMapper;
    
    private final AssociadoMapper associadoMapper;

    @Scheduled(fixedDelayString = UM_MINUTO)
    public void finalizarPautas() {
    	log.info("Processamento iniciado.");
        List<Pauta> pautaList = pautaService.consultarAsNaoFinalizadas();
        for (Pauta pauta : pautaList) {
			if (LocalDateTime.now().isAfter(pauta.getDtFim())) {
				pauta.setFlFinalizada(true);
				this.queueMessagingTemplate.convertAndSend(QUEUE, votacaoResultadoDTOList(pauta));
				this.pautaService.alterar(pauta.getId(), pauta);
				log.info("Pauta finalizada: {}", pauta);
			}
		}
    }

	private List<VotacaoResultadoDTO> votacaoResultadoDTOList(Pauta pauta) {
		List<VotacaoResultadoDTO> votacaoResultadoDTOList = new ArrayList<>();
		Optional<VotacaoResultadoDTO> votacaoResultadoDtoSimOptional = votacaoResultado(pauta, VotoEnum.SIM);
		if (votacaoResultadoDtoSimOptional.isPresent()) {
			votacaoResultadoDTOList.add(votacaoResultadoDtoSimOptional.get());
		}
		Optional<VotacaoResultadoDTO> votacaoResultadoDtoNaoOptional = votacaoResultado(pauta, VotoEnum.NAO);
		if (votacaoResultadoDtoNaoOptional.isPresent()) {
			votacaoResultadoDTOList.add(votacaoResultadoDtoNaoOptional.get());
		}
		log.info("Message {} ", votacaoResultadoDTOList);
		return votacaoResultadoDTOList;
	}

	private Optional<VotacaoResultadoDTO> votacaoResultado(Pauta pauta, VotoEnum voto) {
		Optional<List<Votacao>> votacaoSimListOptional = votacaoService.consultar(pauta.getId(), Optional.empty(), Optional.of(voto));
		if (votacaoSimListOptional.isPresent()) {
			List<Votacao> votacaoSimList = votacaoSimListOptional.get();
			VotacaoResultadoDTO votacaoResultadoSimDTO = new VotacaoResultadoDTO();
			votacaoResultadoSimDTO.setVoto(VotoEnumDTO.valueOf(voto.name()));
			votacaoResultadoSimDTO.setPauta(this.pautaMapper.toPautaCompletoDto(pauta));
			votacaoResultadoSimDTO.setAssociados(new ArrayList<>());
			for (Votacao votacao : votacaoSimList) {
				votacaoResultadoSimDTO.getAssociados().add(this.associadoMapper.toAssociadoCompletoDto(votacao.getVotoId().getAssociado()));
			}
			votacaoResultadoSimDTO.setQtdVotos(votacaoResultadoSimDTO.getAssociados().size());
			return Optional.of(votacaoResultadoSimDTO);
		}
		return Optional.empty();
	}
}