package br.com.southsystem.assembleia.dto;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

import br.com.southsystem.assembleia.dto.enums.VotoEnumDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "VotacaoDTO", description = "Objeto de transferência dos dados da votação.")
public class VotacaoDTO implements Serializable {

	private static final long serialVersionUID = 597022010845966821L;

	@NotNull(message = "O campo associado é obrigatório.")
	@Schema(description = "Objeto associado.")
    private AssociadoBasicoDTO associado;
	
	@NotNull(message = "O campo pauta é obrigatório.")
	@Schema(description = "Objeto pauta.")
    private PautaBasicoDTO pauta;

	@NotNull(message = "O campo voto é obrigatório.")
	@Schema(description = "Valor do voto.", example = "SIM")
    private VotoEnumDTO voto;

}