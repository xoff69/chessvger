package com.xoff.chessvger.admin.ui;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // Génère getters, setters, equals, hashCode et toString
@Builder // Fournit un pattern de construction fluide
@NoArgsConstructor // Génère un constructeur sans argument
@AllArgsConstructor // Génère un constructeur avec tous les arguments
public class UserDTO {
 private Long id;
 private String login;
private String description;
 private String password;
 private LocalDateTime dateCreated;
private LocalDateTime dateUpdated;
 private Boolean profil;
 private Long tenantId;
}
