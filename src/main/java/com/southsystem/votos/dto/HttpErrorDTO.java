package com.southsystem.votos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HttpErrorDTO {

    private int status;

    private String error;

    private String message;

    private long timestamp;

    private String detailMessage;

    private String uriPatch;


}
