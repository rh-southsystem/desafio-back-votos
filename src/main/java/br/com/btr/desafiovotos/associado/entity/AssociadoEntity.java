package br.com.btr.desafiovotos.associado.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ASSOCIADO")
@NoArgsConstructor
@Getter
@Setter
public class AssociadoEntity implements Serializable {

	private static final long serialVersionUID = -4175481603224332881L;

	public AssociadoEntity(String nome, String cpf, boolean podeVotar) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.podeVotar = podeVotar;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IDASSOCIADO")
	private Long id;

	@Column(name = "NMASSOCIADO")
	private String nome;

	@Column(name = "NRCPF")
	private String cpf;

	@Column(name = "FGPODEVOTAR")
	private boolean podeVotar;
}
