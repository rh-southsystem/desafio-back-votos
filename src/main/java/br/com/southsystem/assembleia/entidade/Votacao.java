package br.com.southsystem.assembleia.entidade;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.southsystem.assembleia.entidade.enums.VotoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_VOTACAO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Votacao {
	
	@EmbeddedId
	private VotoId votoId;

	@NotNull(message = "O campo voto é obrigatório.")
	@Column(name = "DS_VOTO", nullable = false)
	@Enumerated(EnumType.STRING)
    private VotoEnum voto;

}
