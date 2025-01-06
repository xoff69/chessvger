package com.xoff.chessvger.view;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPackDto {
  private Long id;
  private String packName;
  private String userName;
}
