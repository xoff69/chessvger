package com.xoff.chessvger.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String login;
    private String description;
    private String password;
    private Boolean profil;
    private Long tenantId;

    private String token;
}
