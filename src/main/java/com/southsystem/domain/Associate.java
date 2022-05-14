package com.southsystem.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Associate {
	
	@Id
	private Integer id;
	
	private String cpf;
	private String name;
	private Date creationDate;
	private Date updateDate;
	
}
