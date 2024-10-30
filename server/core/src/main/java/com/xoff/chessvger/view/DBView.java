package com.xoff.chessvger.view;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DBView {

  private long id;
  private String name;
  private String description;
  private int nbgames;
  private String lastUpdate;

}
