package com.xoff.chessvger.view;

import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
  private Long id;
  private String login;
  private String name;
  private String password;

  private Boolean isAdmin;

  private List<PackDto> packDtoList;
}
