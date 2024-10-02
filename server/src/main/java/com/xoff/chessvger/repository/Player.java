package com.xoff.chessvger.repository;

import lombok.Data;

@Data
public class Player {
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

  // Getters et setters
  // ...

  @Override
  public String toString() {
    return "Player{" +
        "fideId='" + fideId + '\'' +
        ", name='" + name + '\'' +
        ", country='" + country + '\'' +
        ", sex='" + sex + '\'' +
        ", title='" + title + '\'' +
        ", rating=" + rating +
        ", birthday=" + birthday +
        '}';
  }
}
