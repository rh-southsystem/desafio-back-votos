package br.com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Voto {

	@GeneratedValue
	@Id
	private Long id;
	
	private boolean voto;

	@NotNull(message="Associado não informado.")
	@ManyToOne
	private Associado associado;
	
	@NotNull(message="Pauta não informada.")
	@ManyToOne
	private Pauta pauta;
	
	
}
