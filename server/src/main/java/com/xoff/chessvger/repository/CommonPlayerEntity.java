package com.xoff.chessvger.repository;

import com.xoff.chessvger.util.Constants;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostLoad;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Persistable;

@Data
@Entity
@Table(name = "common_player")
public class CommonPlayerEntity  implements Persistable<Long> {


  @Id
  private Long id;

  private String fideId;
  private String name;
  private String country;
  private String sex;
  private String title;
  private String wTitle;
  private String oTitle;
  private String foaTitle;
  private String rating;
  private String games;
  private String k;
  private String rapidRating;
  private String rapidGames;
  private String rapidK;
  private String blitzRating;
  private String blitzGames;
  private String blitzK;
  private String birthday;
  private String flag;

  @Override
  public boolean isNew() {
    return true;
  }



  public boolean isFide() {
    return id < Constants.ID_MIN_NON_FIDE_PLAYER;
  }

}
