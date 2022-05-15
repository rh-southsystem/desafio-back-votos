package com.southsystem.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable=false, unique=true)
	@Length(min = 11, max = 11)
	private String cpf;
	
	@Column(nullable = false)
	@Length(min = 3, max = 50)
	private String name;
	
	@Column(nullable = false)
	private LocalDateTime creationDate;
	private LocalDateTime updateDate;
	
}
