package br.com.assembliescorp.domain.dtos.vote;
import br.com.assembliescorp.domain.enuns.ValueVoteDescription;

public record Vote(	
		Long idSession,
		Long idAssociate,
		ValueVoteDescription value			
		) {

}
