package com.southsystem.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.southsystem.domain.enums.AssemblyStatus;

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
	private Integer id;
	private String title;
	private AssemblyStatus status;
	private Date creationDate;
	private Date updateDate;
	private Long duration;
	

}
