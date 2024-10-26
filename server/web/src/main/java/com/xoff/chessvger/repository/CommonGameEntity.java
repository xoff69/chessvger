package com.xoff.chessvger.repository;

import jakarta.persistence.Column;
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
@Data
@Entity
@Table(name = "common_game")
public class CommonGameEntity implements Persistable<Long> {
  @Override
  public boolean isNew() {
    return true;
  }
  @Id
  private Long id;

  private String event;
  private String site;

  @Column(name = "partie_analysee")
  private boolean partieAnalysee;

  @Temporal(TemporalType.DATE)
  private Date date;

  @Temporal(TemporalType.DATE)
  @Column(name = "event_date")
  private Date eventDate;

  private String round;
  private String result;

  private String whitePlayer;
  private String blackPlayer;

  @Column(name = "white_title")
  private String whiteTitle;

  @Column(name = "black_title")
  private String blackTitle;

  @Column(name = "white_elo")
  private int whiteElo;

  @Column(name = "black_elo")
  private int blackElo;

  private String eco;
  private String opening;

  @Column(name = "white_fide_id")
  private long whiteFideId;

  @Column(name = "black_fide_id")
  private long blackFideId;

  @Column(name = "nb_coups")
  private int nbcoups;

  @Column(name = "last_position")
  private int lastPosition;

  @Column(name = "informations_fait_de_jeu")
  private long informationsFaitDeJeu;

  @Column(name = "last_update")
  private long lastUpdate;

  @Column(name = "is_deleted")
  private boolean isDeleted;

  @Column(name = "first_move")
  private String firstMove;

  @Column(length = 3000)
  private String moves;

  private int interet; // stars

  private boolean theorique;

  private boolean favori;
}
