package com.southsystem.domain;

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
public class VotePK {
	
	@ManyToOne
	@JoinColumn(name = "assembly_id")
    private Assembly assembly;

	@ManyToOne
    @JoinColumn(name = "associate_id")
    private Associate associate;

}
