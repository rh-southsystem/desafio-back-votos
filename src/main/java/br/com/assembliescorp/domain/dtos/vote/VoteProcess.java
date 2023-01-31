package br.com.assembliescorp.domain.dtos.vote;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record VoteProcess(
		
		@NotEmpty
		Long idSession
		) {

}
