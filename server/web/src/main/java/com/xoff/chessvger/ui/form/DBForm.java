package com.xoff.chessvger.ui.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class DBForm {

  private long id;

  private String name;
  private String description;
  private int nbgames;

}
