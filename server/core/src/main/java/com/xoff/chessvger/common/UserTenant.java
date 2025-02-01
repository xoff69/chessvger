package com.xoff.chessvger.common;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserTenant {
  private String login;
  private String tenantName;
  private String password;

  private Boolean isAdmin;

}
