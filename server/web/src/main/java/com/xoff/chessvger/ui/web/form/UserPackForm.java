package com.xoff.chessvger.ui.web.form;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPackForm {
  private Long id;
  private long packId;
  private long userId;
}
