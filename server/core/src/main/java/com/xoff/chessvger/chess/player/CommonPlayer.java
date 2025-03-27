package com.xoff.chessvger.chess.player;

import com.xoff.chessvger.util.Constants;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// TODO virer serialiszble
public class CommonPlayer implements Serializable {


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


}
