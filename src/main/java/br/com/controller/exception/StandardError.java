package br.com.controller.exception;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class StandardError implements Serializable {

	private Long timeStamp;
	private Integer statusCode;
	private String response;
	private String error;
	private String path;
}
