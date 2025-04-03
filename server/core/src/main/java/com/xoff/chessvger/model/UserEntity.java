package com.xoff.chessvger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements Serializable {
    private Long id;

    private String login;

    private String description;

    private String password;

    private LocalDateTime dateCreated;

    private LocalDateTime dateUpdated;

    private Boolean profil;

    private TenantEntity tenant;
}
