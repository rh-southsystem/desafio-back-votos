package br.com.southsystem.assembleia.dto;
import java.io.Serializable;

import javax.validation.constraints.NotNull;

import br.com.southsystem.assembleia.anotacoes.ValorDoEnum;
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
@Schema(name = "VotoDTO", description = "Objeto de transferência dos dados do voto.")
public class VotoDTO implements Serializable {

	private static final long serialVersionUID = 597022010845966821L;

	@NotNull(message = "O campo idAssociado é obrigatório.")
	@Schema(description = "Identificador do associado.", example = "1")
    private Long idAssociado;
	
	@NotNull(message = "O campo idPauta é obrigatório.")
	@Schema(description = "Identificador da pauta.", example = "1")
    private Long idPauta;

	@ValorDoEnum(message = "Valores permitidos são: SIM ou NAO", enumClass = VotoEnumDTO.class)
	@NotNull(message = "O campo voto é obrigatório.")
	@Schema(description = "Valor do voto.", example = "SIM")
    private String voto;

}