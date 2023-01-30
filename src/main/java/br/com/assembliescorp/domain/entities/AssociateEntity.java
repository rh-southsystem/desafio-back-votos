package br.com.assembliescorp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.assembliescorp.domain.dtos.associate.AssociateCreateDTO;
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
@Table(name = "associate")
public class AssociateEntity  extends DefaultEntityModel {
	
	@Column(nullable = false)
    private String name;
	
	@Column(nullable = false, length = 14)
    private String cpf;
	
	public AssociateEntity(AssociateCreateDTO associateCreateDTO){
		this.name = associateCreateDTO.name();
		this.cpf = associateCreateDTO.cpf();
	}

}
