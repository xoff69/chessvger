package com.xoff.chessvger.common;

import com.xoff.chessvger.view.PackDto;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserTenant {
  private Long id;
  private String login;
  private String name;
  private String password;

  private Boolean isAdmin;

}
