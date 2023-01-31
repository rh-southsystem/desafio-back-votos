package br.com.assembliescorp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.assembliescorp.domain.dtos.vote.VoteDTO;
import br.com.assembliescorp.domain.enuns.ValueVoteDescription;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "votes")
public class VoteEntity extends DefaultEntityModel {	
		
	@ManyToOne
	@JoinColumn(name = "ruling_id", nullable = false)
	private RulingEntity ruling;
	
	@ManyToOne
	@JoinColumn(name = "session_id", nullable = false)
	private SessionEntity session;
	
	@ManyToOne
	@JoinColumn(name = "associate_id", nullable = false)
	private AssociateEntity associate;
	
	private Boolean apurated = Boolean.FALSE;
	
	@Enumerated(EnumType.STRING)
	@JoinColumn(name = "value_vote", nullable = false)
	private ValueVoteDescription valueVote;
}
