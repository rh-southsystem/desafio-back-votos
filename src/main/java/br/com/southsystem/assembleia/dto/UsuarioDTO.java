package br.com.southsystem.assembleia.dto;

import br.com.southsystem.assembleia.entidade.enums.CpfStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO {
	
	private CpfStatusEnum status;

}
