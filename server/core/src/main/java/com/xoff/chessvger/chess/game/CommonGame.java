package com.xoff.chessvger.chess.game;

import java.io.Serializable;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonGame implements Serializable {

  private Long id;

  private String whitePlayer;
  private String blackPlayer;
  private String event;
  private String site;

  private boolean partieAnalysee;

  private Date date;

  private Date eventDate;

  private String round;
  private String result;

  private String whiteTitle;

  private String blackTitle;

  private int whiteElo;

  private int blackElo;

  private String eco;
  private String opening;

  private long whiteFideId;

  private long blackFideId;

  private int nbcoups;

  private long lastPosition;

  private long informationsFaitDeJeu;

  private long lastUpdate;

  private boolean isDeleted;

  private String firstMove;

  private String moves;

  private int interet; // stars

  private boolean theorique;

  private boolean favori;
}
