package com.southsystem.dto;

import java.time.LocalDateTime;

import com.southsystem.domain.enums.AssemblyStatus;
import com.southsystem.domain.enums.VoteChoice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssemblyReadDTO {
	
	private Integer id;
	private String title;
	private String description;
	private String status;
	private LocalDateTime startDate;
	private LocalDateTime finishDate;
	private String votesResult;
	private Float percentage;
	private Long duration;
	
	public void setStatus(Integer status) {
		this.status = AssemblyStatus.toEnum(status).getDescription();
	}
	
	public void setVotesResult(Integer voteChoice) {
		this.votesResult = voteChoice != null ? VoteChoice.toEnum(voteChoice).getDescription()
				: null;
	}

}
