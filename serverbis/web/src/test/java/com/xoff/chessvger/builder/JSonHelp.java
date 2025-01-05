package com.xoff.chessvger.builder;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSonHelp {

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
