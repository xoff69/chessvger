package com.xoff.chessvger.ui.web.controller;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
public class MyErrorController implements ErrorController {

  @RequestMapping(value = "/error", method = RequestMethod.GET)
  public String handleError(HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    log.error(" ----> error manager " + request);
    log.error(" error manager " + request.getContextPath());
    log.error(" error manager url " + request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));
    if (status != null) {
      Integer statusCode = Integer.valueOf(status.toString());
      log.error(" 2) error manager " + status);
    }
    return "error";
  }

}
