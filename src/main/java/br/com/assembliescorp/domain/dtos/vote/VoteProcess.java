package br.com.assembliescorp.domain.dtos.vote;

import jakarta.validation.constraints.NotEmpty;

public record VoteProcess(
		
		@NotEmpty
		Long idSession
		) {

}
