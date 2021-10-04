package br.com.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties
public class VotoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	Long cpf;
	String teste;
	Long idPauta;
	boolean voto;
 
    public String toJson() {
    	Gson gson = new Gson();
        return gson.toJson(this);
    }
}
