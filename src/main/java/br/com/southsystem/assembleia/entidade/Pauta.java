package br.com.southsystem.assembleia.entidade;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TB_PAUTA")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name = "TB_PAUTA_SEQ", sequenceName = "TB_PAUTA_SEQ", allocationSize = 1)
public class Pauta implements Serializable {
	
	private static final long serialVersionUID = -8221106341995879973L;

	@Id
	@Column(name = "ID_PAUTA")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TB_PAUTA_SEQ")
	private Long id;

	@NotNull(message = "O campo descrição é obrigatório.")
	@NotBlank(message = "O campo descrição é obrigatório.")
	@Column(name = "DS_PAUTA", nullable = false)
    private String descricao;

	@NotNull(message = "O campo data inicío é obrigatório.")
    @Column(name = "DT_INICIO", nullable = false)
    private LocalDateTime dtInicio;

	@NotNull(message = "O campo data fim é obrigatório.")
    @Column(name = "DT_FIM", nullable = false)
    private LocalDateTime dtFim;
	
	@NotNull(message = "O campo excluida é obrigatório.")
	@Column(name = "FL_EXCLUIDA", nullable = false)
    private boolean flExcluida;
	
	@NotNull(message = "O campo finalizada é obrigatório.")
	@Column(name = "FL_FINALIZADA", nullable = false)
    private boolean flFinalizada;

}
