package com.southsystem.dto;

import com.southsystem.domain.VotePK;
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
	
	private VotePK id;
	private String voteChoice;
	
	public void setVoteChoice(Integer voteChoice) {
		this.voteChoice = VoteChoice.toEnum(voteChoice).getDescription();
	}

}
