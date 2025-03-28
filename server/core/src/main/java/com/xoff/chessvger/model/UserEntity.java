package com.xoff.chessvger.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity  implements Serializable {
  private Long id;

  private String login;

  private String description;

  private String password;

  private LocalDateTime dateCreated;

  private LocalDateTime dateUpdated;

  private Boolean profil;

  private TenantEntity tenant;
}
