package br.com.assembliescorp.domain.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.assembliescorp.domain.dtos.session.SessionCreateDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
@Table(name = "sessions")
public class SessionEntity extends DefaultEntityModel {
	
	@Column(nullable = false)
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "ruling_id", nullable = false)
	private RulingEntity ruling;

	@Column(nullable = false)
	private Long minutes;

	@Column(nullable = false)
	private LocalDateTime begin;

	@Column
	private LocalDateTime finish;

	@Column(columnDefinition = "TEXT")
	private String result;

	public SessionEntity(SessionCreateDTO sessionCreateDTO) {
		this.setId(sessionCreateDTO.id());
		this.setName(sessionCreateDTO.name());
//		this.ruling.setId(sessionCreateDTO.idRuling());
		this.setMinutes(sessionCreateDTO.minutes());

	}

	@PrePersist
	protected void onCreateSession() {
		begin = LocalDateTime.now();
	}
}
