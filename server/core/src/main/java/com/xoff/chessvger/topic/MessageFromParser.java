package com.xoff.chessvger.topic;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageFromParser {
  private long tenantId;
  private long correlationId;
  public ResultAction result;
  public String message;

  public static String toJSon(MessageFromParser messageFromParser){
    String jsonMessage = "{\"tenantId\":\""+messageFromParser.tenantId+"\", \"message\":\""+
        messageFromParser.getMessage()+"\", \"result\":\""
        +messageFromParser.getResult().toString()+"\"}";
    
   return  URLEncoder.encode(jsonMessage, StandardCharsets.UTF_8);

  }
}
