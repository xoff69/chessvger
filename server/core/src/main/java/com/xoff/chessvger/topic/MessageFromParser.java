package com.xoff.chessvger.topic;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageFromParser {
  private long tenantId;
  private long correlationId;
  public ResultAction result;
  public String message;
}
