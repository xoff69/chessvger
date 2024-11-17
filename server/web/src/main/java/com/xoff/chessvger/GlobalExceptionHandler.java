package com.xoff.chessvger;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler({NoHandlerFoundException.class})
  public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex,
                                                              HttpServletRequest httpServletRequest) {
    System.out.println("No handler found exception" + ex.getMessage());
    log.info("No handler found exception" + ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
        .body("No handler found exception" + ex.getMessage());
  }
}
