package br.com.southsystem.assembleia.dto;
import java.io.Serializable;
import java.util.List;

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
@Schema(name = "VotacaoResultadoDTO", description = "Objeto de transferência do resultado da votação.")
public class VotacaoResultadoDTO implements Serializable {

	private static final long serialVersionUID = 597022010845966821L;

	@Schema(description = "Objeto pauta.")
    private PautaCompletoDTO pauta;

	@Schema(description = "Valor do voto.", example = "SIM")
    private VotoEnumDTO voto;
	
	@Schema(description = "Quantidade dos votos", example = "10")
    private Integer qtdVotos;
	
	@Schema(description = "Lista dos associados.")
    private List<AssociadoCompletoDTO> associados;

}