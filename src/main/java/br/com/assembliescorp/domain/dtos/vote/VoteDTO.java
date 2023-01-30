package br.com.assembliescorp.domain.dtos.vote;
import br.com.assembliescorp.domain.entities.VoteEntity;
import br.com.assembliescorp.domain.enuns.ValueVoteDescription;

public record VoteDTO(
		Long id,
		Long idSession,
		Long idRuling,
		Long idAssociate,
		ValueVoteDescription value			
		) {
	
	public VoteDTO(VoteEntity voteEntity) {
		this(voteEntity.getId(), voteEntity.getSession().getId(), voteEntity.getRuling().getId(), voteEntity.getAssociate().getId(), voteEntity.getValueVote());
	}

}
