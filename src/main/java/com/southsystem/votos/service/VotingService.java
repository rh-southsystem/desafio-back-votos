package com.southsystem.votos.service;

import com.southsystem.votos.dto.CPFIntegrationDTO;
import com.southsystem.votos.dto.VoteDTO;
import com.southsystem.votos.dto.VotingResultDTO;
import com.southsystem.votos.entity.Agenda;
import com.southsystem.votos.entity.Associate;
import com.southsystem.votos.entity.Voting;
import com.southsystem.votos.enums.CPFIntegrationStatusEnum;
import com.southsystem.votos.enums.VoteTypeEnum;
import com.southsystem.votos.exception.BadRequestException;
import com.southsystem.votos.exception.NotFoundException;
import com.southsystem.votos.integration.CPFIntegration;
import com.southsystem.votos.repository.AssociateRepository;
import com.southsystem.votos.repository.VotingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class VotingService {

    private final VotingRepository votingRepository;

    private final AssociateRepository associateService;

    private final CPFIntegration cpfIntegration;

    private final AgendaService agendaService;

    public void sendVote(VoteDTO voteDTO) {

        if(!isOpenedAgenda(voteDTO.getAgendaId())) {
            log.warn("Sessão {} indisponível para votação", voteDTO.getAgendaId());
            throw new BadRequestException("Ocorreu um erro pois a sessão está indisponível para votação: " + voteDTO);
        }

        Associate associate = associateService.findByCpf(voteDTO.getCpf());

        if(associate == null || associate.getId() == null) {
            log.warn("Associado não encontrado: {}", voteDTO.getAgendaId());
            throw new BadRequestException("Associado não encontrado na base de dados: " + voteDTO.getCpf());
        }

        try {
            ResponseEntity<CPFIntegrationDTO> response = cpfIntegration.isCPFValid(voteDTO.getCpf());

            if(response.getBody().getStatus().equals(CPFIntegrationStatusEnum.UNABLE_TO_VOTE)) {
                log.warn("CPF não habilitado para votar: {} ", voteDTO.getCpf());
                throw new BadRequestException("Ocorreu um erro pois o CPF não está habilitado para votar: " + voteDTO.getCpf()  );
            }

            Voting voting = Voting.builder()
                    .associate(associate)
                    .agenda(Agenda.builder().id(voteDTO.getAgendaId()).build())
                    .voteType(voteDTO.getVoteType())
                    .build();

            voting = votingRepository.save(voting);
            log.info("Voto registrado: {}", voting);
        } catch (NotFoundException e) {
            log.warn("CPF inválido: {} ", voteDTO.getAgendaId());
            throw new NotFoundException("Associado não encontrado na base de dados.");
        } catch (DataIntegrityViolationException e) {
            log.warn("CPF {} já voltou na pauta {} ", voteDTO.getCpf(), voteDTO.getAgendaId());
            throw new BadRequestException("Ocorreu um erro pois o associado já voltou nessa Pauta");
        }
    }

    private boolean isOpenedAgenda(Long agendaId) {
        boolean result = false;
        Optional<Agenda> optional = agendaService.findById(agendaId);
        if(optional.isPresent()) {
            Agenda agenda = optional.get();
            result = !LocalDateTime.now().isAfter(agenda.getDtEnd());
        }
        return result;
    }

    public VotingResultDTO getVotingResult(Long agendaId) {
        VotingResultDTO result = VotingResultDTO.builder()
                .agendaId(agendaId)
                .countVotesNo(votingRepository.countVotesByVoteTypeAndAgendaId(VoteTypeEnum.NAO.name(), agendaId))
                .countVotesYes(votingRepository.countVotesByVoteTypeAndAgendaId(VoteTypeEnum.SIM.name(), agendaId))
                .build();

        return result;
    }
}
