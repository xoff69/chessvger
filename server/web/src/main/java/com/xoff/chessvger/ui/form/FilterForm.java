package com.xoff.chessvger.ui.form;

import com.xoff.chessvger.util.Constants;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FilterForm {

  public static final List<String> listeNumber =
      Collections.unmodifiableList(Arrays.asList("0", "1", "2", "3", "4", "5", "6", "7", "8"));
  public static final List<String> listeInterval =
      Collections.unmodifiableList(Arrays.asList("<", "=", ">", ".."));
  public static final List<String> titles = Constants.FIDE_TITLE;


  private String ecoSelection;
  private String coupsSelection;
  private String dateSelection;
  private long bdId;

  private boolean resultat10;
  private boolean resultat12;
  private boolean resultat01;
  /**
   * recherche position
   */
  private boolean positionjcTraitBlanc;
  private boolean positionjcTraitNoir;
  private boolean positionjcTraitInconnu;

  private boolean positionjcPetitRoqueBlanc;
  private boolean positionjcGrandRoqueBlanc;

  private boolean positionjcPetitRoqueNoir;
  private boolean positionjcGrandRoqueNoir;

  private String positionjtfFEN;


  private long id;
  private boolean isSystem;
  private String nomFiltre;
  private boolean casGeneral;
  private boolean casOtherCriteria;
  private boolean casPosition;
  /**
   * - y n
   */
  private char favori;
  /**
   * - 1 2 3 4 5
   */
  private char interet;
  /**
   * - y n
   */
  private char theorique;
  private String nbJourSinceUpdate;
  // cas general
  private boolean campIndifferent;
  private String event;
  private String site;
  private String date1;
  private String date2;
  //TODO enum @see EnumModeRecherche
  private boolean modeApproximatif;
  private String selectedModeDate;
  private String selectedModeCoups;
  private String selectedModeEco;
  private String round;
  private String white;
  private String black;
  private int result;
  private String whiteTitle;
  private String blackTitle;
  private String whiteElo;
  private String blackElo;
  private String eco1;
  private String eco2;
  private String whiteFideId;
  private String blackFideId;
  private boolean includeDateNull;
  private String nbcoup1;
  private String nbcoup2;
  // cas position
  private long zobrist;
  // cas materiel
  private int nbdamenoir;
  private int nbpionnoir;
  private int nbtournoir;
  private int nbfounoir;
  private int nbcavaliernoir;
  private int nbdameblanc;
  private int nbpionblanc;
  private int nbtourblanc;
  private int nbfoublanc;
  private int nbcavalierblanc;
  private boolean isMaterialPrecis;
  // fait jeu
// @see maskFaitsDeJeu
  private boolean isGrandRoqueBlanc;
  private boolean isGrandRoqueNoir;
  private boolean isPetitRoqueBlanc;
  private boolean isPetitRoqueNoir;
  private boolean bxf7;
  private boolean bxh6;
  private boolean bxf2;
  private boolean bxh3;
  private boolean doublecheck;
  private boolean ep;
  private boolean foudecouleuropposee;
  private boolean pat;
  private boolean pionsdoubles;
  private boolean pionstrples;


}
