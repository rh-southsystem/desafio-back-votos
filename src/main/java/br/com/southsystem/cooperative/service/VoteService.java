package br.com.southsystem.cooperative.service;

import br.com.southsystem.cooperative.service.dto.AffiliatedDTO;
import br.com.southsystem.cooperative.service.dto.VoteCreateRequestDTO;
import br.com.southsystem.cooperative.service.dto.VoteDTO;
import org.springframework.transaction.annotation.Transactional;

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
}
