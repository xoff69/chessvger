package com.xoff.chessvger.repository;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
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
@Table(name = "database")
public class DatabaseEntity implements Persistable<Long> {
  @Override
  public boolean isNew() {
    return true;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 255)
  private String name;

  private String description;

  @Column(name = "date_created", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
  private LocalDateTime dateCreated;

  @Column(name = "date_update")
  private LocalDateTime dateUpdate;
}
