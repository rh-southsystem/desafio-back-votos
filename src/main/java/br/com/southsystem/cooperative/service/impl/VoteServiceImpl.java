package br.com.southsystem.cooperative.service.impl;

import br.com.southsystem.cooperative.domain.Vote;

import br.com.southsystem.cooperative.exception.BadRequestAlertException;
import br.com.southsystem.cooperative.exception.CpfNotFoundException;
import br.com.southsystem.cooperative.exception.CpfUnableToVoteException;
import br.com.southsystem.cooperative.exception.SessionIsNotOpenException;
import br.com.southsystem.cooperative.repository.VoteRepository;
import br.com.southsystem.cooperative.service.AffiliatedService;
import br.com.southsystem.cooperative.service.SessionService;
import br.com.southsystem.cooperative.service.VoteService;
import br.com.southsystem.cooperative.service.dto.*;
import br.com.southsystem.cooperative.service.mapper.VoteMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class VoteServiceImpl implements VoteService {

    private final VoteRepository voteRepository;

    private final VoteMapper voteMapper;

    private final AffiliatedService affiliatedService;

    private final SessionService sessionService;

    private final WebClient webClient;

    @Value("${cooperative.cpfExternalApi.url}")
    private String cpfExternalApiUrl;

    @Value("${cooperative.cpfExternalApi.results.unableToVote}")
    private String UNABLE_TO_VOTE;

    public VoteServiceImpl(VoteRepository voteRepository, VoteMapper voteMapper,
                           AffiliatedService affiliatedService, SessionService sessionService) {
        this.voteRepository = voteRepository;
        this.voteMapper = voteMapper;
        this.affiliatedService = affiliatedService;
        this.sessionService = sessionService;

        this.webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Save a vote.
     *
     * @param voteCreateRequestDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public VoteDTO vote(VoteCreateRequestDTO voteCreateRequestDTO) {
        log.debug("Request to save Vote : {}", voteCreateRequestDTO);

        SessionDTO sessionDTO = sessionService.findOne(voteCreateRequestDTO.getSessionId())
                .orElseThrow(() -> new EntityNotFoundException("The Session does not exist!"));
        if (!sessionDTO.isOpen()) {
            throw new SessionIsNotOpenException("The session is closed!");
        }

        VoteDTO voteDTO = voteMapper.toDto(voteCreateRequestDTO);
        voteDTO.setVoteDateTime(LocalDateTime.now());
        AffiliatedDTO affiliated = affiliatedService.findOrCreateByCpf(voteDTO.getAffiliatedCpf());

        if (hasAffiliatedVoteInSessionByAffiliatedCpfAndSessionId(affiliated.getCpf(), voteCreateRequestDTO.getSessionId())) {
            throw new BadRequestAlertException("The affiliated has benn voted in the session!");
        }

        CpfExternalApiResultDTO cpfExternalApiResultDTO = verifyCpfIsAbleToVote(affiliated.getCpf()).block();

        if (cpfExternalApiResultDTO.getStatus().equals(UNABLE_TO_VOTE)) {
            throw new CpfUnableToVoteException("The CPF is unable to vote.");
        }

        voteDTO.setAffiliatedId(affiliated.getId());
        Vote vote = voteMapper.toEntity(voteDTO);
        vote = voteRepository.save(vote);
        return voteMapper.toDto(vote);
    }


    /**
     * Get one vote by cpf Affliated.
     *
     * @param cpf the cpf of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<VoteDTO> findOneByAffiliatedCpfAndSessionId(String cpf, Long sessionId) {
        log.debug("Method to find one by affiliatedCpf and sessionId : {} {}", cpf, sessionId);
        return voteRepository.findFirstByAffiliatedCpfAndSessionId(cpf, sessionId)
                .map(voteMapper::toDto);
    }

    /**
     * Get one vote by cpf Affliated.
     *
     * @param cpf the cpf of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public boolean hasAffiliatedVoteInSessionByAffiliatedCpfAndSessionId(String cpf, Long sessionId) {
        log.debug("Request to get Vote by cpf affiliated and sessionId : {} {}", cpf, sessionId);
        Optional<VoteDTO> vote = findOneByAffiliatedCpfAndSessionId(cpf, sessionId);
        return vote != null && vote.isPresent() && !vote.isEmpty() ? true : false;
    }

    /**
     * Verify if the cpf is able to vote.
     *
     * @param cpf
     */
    @Override
    public Mono<CpfExternalApiResultDTO> verifyCpfIsAbleToVote(String cpf) {
        log.debug("Request to verify cpf is able to vote: {}", cpf);
        return this.webClient.method(HttpMethod.GET).uri(cpfExternalApiUrl + "/{cpf}", cpf).retrieve()
                .onStatus(status -> status.value() == HttpStatus.NOT_FOUND.value(),
                        response -> Mono.error(new CpfNotFoundException("The CPF is invalid")))
                .bodyToMono(CpfExternalApiResultDTO.class);
    }
}
