package br.com.southsystem.cooperative.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

public final class CustomResponseEntityExceptionHandler {

    @ExceptionHandler
  public static ResponseEntity handle(ConstraintViolationException exception) {
        List<String> details = new ArrayList<>();
        List<ConstraintViolation> errorMessage = new ArrayList<>(exception.getConstraintViolations());
        for(ConstraintViolation error : errorMessage) {
            details.add(error.getMessage());
        }

        return new ResponseEntity(details.toString(), HttpStatus.BAD_REQUEST);
  }
}
