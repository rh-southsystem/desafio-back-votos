package com.southsystem.dto;

import java.time.LocalDateTime;

import com.southsystem.domain.enums.VoteChoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteReadDTO {
	
	private LocalDateTime creationDate;
	private String voteChoice;
	
	public void setVoteChoice(Integer voteChoice) {
		this.voteChoice = VoteChoice.toEnum(voteChoice).getDescription();
	}

}
