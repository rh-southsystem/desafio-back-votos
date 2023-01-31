package br.com.assembliescorp.infra.handlers;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorMessage {
	
	  private int statusCode;
	  private LocalDateTime timestamp;
	  private String message;
	  private String description;
	  
	  public ErrorMessage(int statusCode, LocalDateTime timestamp, String message, String description) {
		    this.statusCode = statusCode;
		    this.timestamp = timestamp;
		    this.message = message;
		    this.description = description;
		  }

}
