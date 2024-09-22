package com.xoff.chessvger.ui.web.view;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RepertoireDto {

  private long repertoireId;
  private long userId;
  private int color;
  private long databaseId;
}
