package com.southsystem.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

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
	
	@Column(nullable = false)
	private Integer voteChoice;
	
	@Column(nullable = false)
	private LocalDateTime creationDate;

}
