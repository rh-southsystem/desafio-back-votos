package br.com.assembliescorp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.assembliescorp.domain.enuns.ValueVoteDescription;
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
	private RulingEntity rulingEntity;
	
	@ManyToOne
	@JoinColumn(name = "session_id", nullable = false)
	private SessionEntity sessionEntity;
	
	@ManyToOne
	@JoinColumn(name = "associate_id", nullable = false)
	private AssociateEntity associateEntity;
	
	@Enumerated(EnumType.STRING)
	@JoinColumn(name = "value", nullable = false)
	private ValueVoteDescription value;

}
