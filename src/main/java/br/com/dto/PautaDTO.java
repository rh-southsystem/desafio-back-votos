package br.com.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PautaDTO {

	@NotEmpty(message = "Favor informar o Nome da Pauta")
	String nome;
}
