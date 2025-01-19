package com.xoff.chessvger.repository;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.domain.Persistable;
import jakarta.persistence.Id;
@Data
@Entity
@Table(name = "user",schema = "common")
public class UserEntity  implements Persistable<Long> {
  @Override
  public boolean isNew() {
    return true;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true, length = 50)
  private String login;

  private String description;

  @Column(nullable = false, length = 255)
  private String password;

  @Column(name = "date_created", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime dateCreated;

  @Column(name = "date_update")
  private LocalDateTime dateUpdate;

  @Column(nullable = false)
  private Boolean profil;

  @ManyToOne(fetch = FetchType.LAZY) // Relation Many-to-One avec Tenant
  @JoinColumn(name = "tenant_id", nullable = false)
  private TenantEntity tenant;
}
