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
@Table(name = "contract",schema = "admin")
public class ContractEntity  implements Persistable<Long> {
  @Override
  public boolean isNew() {
    return true;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private Long price;

  @Column(nullable = false)
  private Integer durationDay;

  @Column(nullable = false)
  private LocalDateTime startDate;

  @Column(nullable = false)
  private LocalDateTime endDate;

  @Column(nullable = false)
  private Long databaseId;

}
