package br.com.southsystem.assembleia.dto;
import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "AssociadoBasicoDTO", description = "Objeto de transferência dos dados do associado.")
public class AssociadoBasicoDTO implements Serializable {

	private static final long serialVersionUID = 597022010845966821L;

	@NotNull(message = "O campo nome é obrigatório.")
	@NotBlank(message = "O campo nome é obrigatório.")
	@Schema(description = "Nome do associado.", example = "Marcell Araújo Coelho Ribeiro Gomes")
    private String nome;
	
	@NotNull(message = "O campo cpf é obrigatório.")
	@NotBlank(message = "O campo cpf é obrigatório.")
	@Schema(description = "Cpf do associado.", example = "07911761426")
    private String cpf;

}