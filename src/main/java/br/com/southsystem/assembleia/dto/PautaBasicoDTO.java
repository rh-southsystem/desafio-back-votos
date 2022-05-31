package br.com.southsystem.assembleia.dto;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.FutureOrPresent;
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
@Schema(name = "PautaBasicoDTO", description = "Objeto de transferência dos dados da pauta.")
public class PautaBasicoDTO implements Serializable {

	private static final long serialVersionUID = 597022010845966821L;

	@NotNull(message = "O campo descrição é obrigatório.")
	@NotBlank(message = "O campo descrição é obrigatório.")
	@Schema(description = "Descrição da pauta.", example = "Prestacão de contas do ano de 2021.")
    private String descricao;
	
	@FutureOrPresent(message = "O campo data inicío deve ser após a data atual.")
	@NotNull(message = "O campo data inicío é obrigatório.")
	@Schema(description = "Data e hora do inicio da pauta.")
    private LocalDateTime dtInicio;

	@Schema(description = "Data e hora do inicio da pauta.")
    private LocalDateTime dtFim;

}