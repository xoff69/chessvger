package com.xoff.chessvger.ui;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.value.qual.ArrayLen;

@Getter
@Setter
@AllArgsConstructor
public class MockUser {
  private int id;
  private String name;
  private String email;
  private String token;
}
