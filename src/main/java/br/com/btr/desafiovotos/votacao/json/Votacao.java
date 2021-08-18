package br.com.btr.desafiovotos.votacao.json;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import br.com.btr.desafiovotos.pauta.enums.Voto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Votacao implements Serializable {

	private static final long serialVersionUID = 5669599436389590568L;

	@NotNull
	private Long idPauta;

	@NotNull
	private Long idAssociado;

	@NotNull
	private Voto voto;
}
