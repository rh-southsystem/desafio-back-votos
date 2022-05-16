package com.southsystem.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VotePK implements Serializable {
	
	private static final long serialVersionUID = -3028645940480448292L;

	@ManyToOne
	@JoinColumn(name = "assembly_id")
    private Assembly assembly;

	@ManyToOne
    @JoinColumn(name = "associate_id")
    private Associate associate;

}
