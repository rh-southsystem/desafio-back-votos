package br.com.btr.desafiovotos.pauta.json;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sessao implements Serializable {

	private static final long serialVersionUID = -9143272727817098983L;

	@JsonProperty( "timeInMinutes" )
	@NotNull
	private Integer tempoPautaMinutos;
}
