package com.xoff.chessvger.ui.web.controller.tools;

import com.xoff.chessvger.model.UserEntity;

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
                entity.getProfil(),
                entity.getTenant().getId(),
                ""
        );
    }


}
