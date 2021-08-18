package br.com.btr.desafiovotos.votacao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.btr.desafiovotos.associado.entity.AssociadoEntity;
import br.com.btr.desafiovotos.pauta.entity.PautaEntity;
import br.com.btr.desafiovotos.pauta.enums.Voto;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "VOTACAO")
@Getter
@Setter
public class VotacaoEntity implements Serializable {

	private static final long serialVersionUID = -4175481603224332881L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDVOTACAO")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "IDPAUTA")
	private PautaEntity pauta;

	@ManyToOne
	@JoinColumn(name = "IDASSOCIADO")
	private AssociadoEntity associado;

	@Column(name = "DSVOTO")
	@Enumerated(EnumType.STRING)
	private Voto voto;
}
