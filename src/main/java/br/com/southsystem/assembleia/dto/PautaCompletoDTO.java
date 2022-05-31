package br.com.southsystem.assembleia.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "PautaCompletoDTO", description = "Objeto de transferência dos dados da pauta.")
public class PautaCompletoDTO extends PautaBasicoDTO {

	private static final long serialVersionUID = -1120048987206985201L;
	
	@Schema(description = "Identificador da pauta.", example = "1")
    private Long id;
	
	@Schema(description = "Pauta excluída.")
    private boolean flExcluida;
	
}