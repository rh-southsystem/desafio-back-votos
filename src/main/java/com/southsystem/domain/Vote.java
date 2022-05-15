package com.southsystem.domain;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.southsystem.domain.enums.VoteChoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vote {
	
	@EmbeddedId
	private VotePK id;
	private VoteChoice voteChoice;

}
