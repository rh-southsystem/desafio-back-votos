package br.com.btr.desafiovotos.pauta.json;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pauta implements Serializable {

	private static final long serialVersionUID = -4097359271180038080L;

	@JsonProperty( "description" )
	@NotBlank
	private String descricao;
	
	private Long id;
}
