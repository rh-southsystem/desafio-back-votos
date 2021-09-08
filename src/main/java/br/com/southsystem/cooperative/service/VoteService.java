package br.com.southsystem.cooperative.service;

import br.com.southsystem.cooperative.service.dto.CpfExternalApiResultDTO;
import br.com.southsystem.cooperative.service.dto.VoteCreateRequestDTO;
import br.com.southsystem.cooperative.service.dto.VoteDTO;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface VoteService {
    /**
     * Save a vote.
     *
     * @param voteDTO the entity to save.
     * @return the persisted entity.
     */
    VoteDTO vote(VoteCreateRequestDTO voteDTO);


    /**
     * Get the "cpf" vote.
     *
     * @param cpf the cpf of the entity.
     * @return the entity.
     */
    Optional<VoteDTO> findOneByAffiliatedCpfAndSessionId(String cpf, Long sessionId);

    /**
     * Get the "cpf" vote.
     *
     * @param cpf the cpf of the entity.
     * @return if has or not.
     */
    boolean hasAffiliatedVoteInSessionByAffiliatedCpfAndSessionId(String cpf, Long sessionId);

    Mono<CpfExternalApiResultDTO> verifyCpfIsAbleToVote(String cpf);
}
