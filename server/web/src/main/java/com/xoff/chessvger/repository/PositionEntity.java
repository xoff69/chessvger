package com.xoff.chessvger.repository;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Date;
import lombok.Data;
import org.springframework.data.domain.Persistable;
import java.util.List;
@Data
@Entity
@Table(name = "position_games")
public class PositionEntity  implements Persistable<Long> {
  @Override
  public boolean isNew() {
    return true;
  }
  @Id
  private Long id;

  private long position;

  @ElementCollection
  private List<Long> games;

}
