package br.com.btr.desafiovotos.pauta.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PAUTA")
@Getter
@Setter
public class PautaEntity implements Serializable {

	private static final long serialVersionUID = -4175481603224332881L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDPAUTA")
	private Long id;

	@Column(name = "DSPAUTA")
	private String descricao;
	
	@Column( name = "DTINICIO" )
	private LocalDateTime inicio;
	
	@Column( name = "DTFIM" )
	private LocalDateTime fim;
}
