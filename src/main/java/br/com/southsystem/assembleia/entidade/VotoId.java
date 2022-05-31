package br.com.southsystem.assembleia.entidade;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class VotoId implements Serializable {
	
	private static final long serialVersionUID = -4255644164926787825L;

	@NotNull(message = "O campo idAssociado é obrigatório.")
	@JoinColumn(name = "FK_ASSOCIADO")
	@ManyToOne
	public Associado associado;

	@NotNull(message = "O campo idPauta é obrigatório.")
	@JoinColumn(name = "FK_PAUTA")
	@ManyToOne
	public Pauta pauta;

}