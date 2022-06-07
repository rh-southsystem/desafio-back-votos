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
@Schema(name = "AssociadoCompletoDTO", description = "Objeto de transferência dos dados do associado.")
public class AssociadoCompletoDTO extends AssociadoBasicoDTO {

	private static final long serialVersionUID = -1120048987206985201L;
	
	@Schema(description = "Identificador do associado.", example = "1")
    private Long id;
	
	@Schema(description = "Associado excluído.")
    private boolean flExcluido;
	
}