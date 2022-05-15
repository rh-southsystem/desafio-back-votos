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
public class Assembly {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	@Length(min = 3, max = 50)
	private String title;
	
	@Column(nullable = false)
	@Length(min = 5, max = 255)
	private String description;
	
	@Column(nullable = false)
	private Integer status;
	
	@Column(nullable = false)
	private LocalDateTime creationDate;
	private LocalDateTime updateDate;
	
	@Column(nullable = false)
	private Long duration;
	

}
