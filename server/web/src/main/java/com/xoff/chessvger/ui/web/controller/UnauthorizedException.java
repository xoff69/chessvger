package com.xoff.chessvger.ui.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO sortir de la
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public  class UnauthorizedException extends RuntimeException {
  public UnauthorizedException(String message) {
    super(message);
  }
}

