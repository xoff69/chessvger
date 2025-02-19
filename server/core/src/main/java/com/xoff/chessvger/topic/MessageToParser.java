package com.xoff.chessvger.topic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageToParser {
  private String folderToParse;
  private String schema;
  private String databaseName;
  private ActionQueue actionQueue;
  private long tenantId;
  private long correlationId;
}
