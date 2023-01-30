package br.com.assembliescorp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.assembliescorp.domain.RulingCreateDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "rullies")
public class RulingEntity extends DefaultEntityModel {
	
	@Column(nullable = false)
    private String name;
	
	public RulingEntity(RulingCreateDTO rulingCreateDTO) {
		this.name = rulingCreateDTO.name();
	}

}
