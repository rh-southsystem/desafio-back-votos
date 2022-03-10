package com.southsystem.votos.exception.handler;


import com.southsystem.votos.dto.HttpErrorDTO;
import com.southsystem.votos.enums.ErrorTypeEnum;
import com.southsystem.votos.exception.BadRequestException;
import com.southsystem.votos.exception.NotFoundException;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

import static org.springframework.http.ResponseEntity.status;

@ControllerAdvice
public class HttpExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<HttpErrorDTO> handleNotFoundException(NotFoundException e, HttpServletRequest request) {
        HttpErrorDTO error = new HttpErrorDTO(
                HttpStatus.NOT_FOUND.value(),
                ErrorTypeEnum.NOT_FOUND.toString(),
                ErrorTypeEnum.NOT_FOUND.getMessage(),
                new Date().getTime(),
                e.getMessage(),
                request.getRequestURI());
        return status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<HttpErrorDTO> handleBadRequestException(BadRequestException e, HttpServletRequest request) {

        HttpErrorDTO error = new HttpErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                ErrorTypeEnum.BAD_REQUEST.toString(),
                ErrorTypeEnum.BAD_REQUEST.getMessage(),
                new Date().getTime(),
                e.getMessage(),
                request.getRequestURI());
        return status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<HttpErrorDTO> handleFeignException(FeignException e, HttpServletRequest request) {
        HttpErrorDTO error = new HttpErrorDTO(
                HttpStatus.BAD_REQUEST.value(),
                ErrorTypeEnum.BAD_REQUEST.toString(),
                ErrorTypeEnum.BAD_REQUEST.getMessage(),
                new Date().getTime(),
                e.getMessage(),
                request.getRequestURI());
        return status(HttpStatus.NOT_FOUND).body(error);
    }


}
