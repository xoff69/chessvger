package com.xoff.chessvger.ui;

import com.xoff.chessvger.repository.UserEntity;

public class UserMapper {
  public static UserDTO toDto(UserEntity entity) {
    if (entity == null) {
      return null;
    }
    return new UserDTO(
        entity.getId(),
        entity.getLogin(),
        entity.getDescription(),
        entity.getPassword(),
        entity.getDateCreated(),
        entity.getDateUpdated(),
        entity.getProfil(),
        entity.getTenant().getId()
    );
  }


}
