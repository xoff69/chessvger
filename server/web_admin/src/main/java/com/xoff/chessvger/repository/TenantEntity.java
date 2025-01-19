package com.xoff.chessvger.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.domain.Persistable;
import jakarta.persistence.Id;
@Data
@Entity
@Table(name = "tenant",schema = "common")
public class TenantEntity  implements Persistable<Long> {
  @Override
  public boolean isNew() {
    return true;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "tenant_id")
  private Long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;
}
