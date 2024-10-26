package com.xoff.chessvger.chess.filter;

import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.util.FaitsDeJeuUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class Filter implements Serializable {
  private static final long serialVersionUID = 4849745656751957541L;

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
  private int nbJourSinceUpdate;
  // cas general
  private boolean isCampIndifferent;
  private String event;
  private String site;
  private String date1;
  private String date2;
  //TODO enum @see EnumModeRecherche
  private boolean modeApproximatif;
  private int selectedModeDate;
  private int selectedModeCoups;
  private int selectedModeEco;
  private String round;
  private String white;
  private String black;
  private int result;
  private String whiteTitle;
  private String blackTitle;
  private int whiteElo;
  private int blackElo;
  private String eco1;
  private String eco2;
  private long whiteFideId;
  private long blackFideId;
  private boolean includeDateNull;
  private int nbcoup1;
  private int nbcoup2;
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


  public Filter(String nom, long id) {
    //pour initialiser par defaut
    this.id = id;
    this.nomFiltre = nom;

    isSystem = false;
    this.setCasGeneral(true);
    this.setSelectedModeCoups(Constants.NO_SEL);
    this.setSelectedModeEco(Constants.NO_SEL);
    this.setSelectedModeDate(Constants.NO_SEL);

    favori = '-';
    interet = '-';
    theorique = '-';
    nbJourSinceUpdate = 0;

    event = StringUtils.EMPTY;
    site = StringUtils.EMPTY;
    date1 = StringUtils.EMPTY;
    date2 = StringUtils.EMPTY;

    round = StringUtils.EMPTY;
    white = StringUtils.EMPTY;
    black = StringUtils.EMPTY;
    whiteTitle = StringUtils.EMPTY;
    blackTitle = StringUtils.EMPTY;
    eco1 = StringUtils.EMPTY;
    eco2 = StringUtils.EMPTY;
    whiteFideId = 0L;
    blackFideId = 0L;

  }


  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeLong(getId());

    out.writeUTF(getNomFiltre());
    out.writeBoolean(casGeneral);
    out.writeBoolean(casOtherCriteria);
    out.writeBoolean(casPosition);

    out.writeBoolean(isSystem());
    out.writeBoolean(isCampIndifferent());
    out.writeUTF(getEvent());
    out.writeUTF(getSite());

    out.writeChar(getFavori());
    out.writeChar(getInteret());
    out.writeChar(getTheorique());

    out.writeInt(getNbJourSinceUpdate());

    out.writeUTF(getDate1());
    out.writeUTF(getDate2());
    out.writeBoolean(isModeApproximatif());
    out.writeInt(getSelectedModeDate());
    out.writeInt(getSelectedModeCoups());
    out.writeInt(getSelectedModeEco());
    out.writeUTF(getRound());
    out.writeUTF(getWhite());
    out.writeUTF(getBlack());
    out.writeInt(getResult());
    out.writeUTF(getWhiteTitle());
    out.writeUTF(getBlackTitle());

    out.writeInt(getWhiteElo());
    out.writeInt(getBlackElo());

    out.writeUTF(getEco1());
    out.writeUTF(getEco2());

    out.writeLong(getWhiteFideId());
    out.writeLong(getBlackFideId());
    out.writeBoolean(isIncludeDateNull());
    out.writeInt(getNbcoup1());
    out.writeInt(getNbcoup2());
    out.writeLong(getZobrist());

    out.writeInt(getNbdamenoir());
    out.writeInt(getNbpionnoir());
    out.writeInt(getNbtournoir());
    out.writeInt(getNbfounoir());
    out.writeInt(getNbcavaliernoir());

    out.writeInt(getNbdameblanc());
    out.writeInt(getNbpionblanc());
    out.writeInt(getNbtourblanc());
    out.writeInt(getNbfoublanc());
    out.writeInt(getNbcavalierblanc());

    out.writeBoolean(isMaterialPrecis());

    out.writeBoolean(isGrandRoqueBlanc());
    out.writeBoolean(isGrandRoqueNoir());
    out.writeBoolean(isPetitRoqueBlanc());
    out.writeBoolean(isPetitRoqueNoir());

    out.writeBoolean(isBxf7());
    out.writeBoolean(isBxh6());
    out.writeBoolean(isBxf2());
    out.writeBoolean(isBxh3());
    out.writeBoolean(isDoublecheck());
    out.writeBoolean(isEp());
    out.writeBoolean(isFoudecouleuropposee());
    out.writeBoolean(isPat());
    out.writeBoolean(isPionsdoubles());
    out.writeBoolean(isPionstrples());
  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

    long id = in.readLong();
    setId(id);
    setNomFiltre(in.readUTF());

    setCasGeneral(in.readBoolean());
    setCasOtherCriteria(in.readBoolean());
    setCasPosition(in.readBoolean());


    setSystem(in.readBoolean());
    setCampIndifferent(in.readBoolean());

    setEvent(in.readUTF());
    setSite(in.readUTF());

    setFavori(in.readChar());
    setInteret(in.readChar());
    setTheorique(in.readChar());

    setNbJourSinceUpdate(in.readInt());

    setDate1(in.readUTF());
    setDate2(in.readUTF());

    setModeApproximatif(in.readBoolean());
    setSelectedModeDate(in.readInt());
    setSelectedModeCoups(in.readInt());
    setSelectedModeEco(in.readInt());
    setRound(in.readUTF());
    setWhite(in.readUTF());
    setBlack(in.readUTF());
    setResult(in.readInt());
    setWhiteTitle(in.readUTF());
    setBlackTitle(in.readUTF());

    setWhiteElo(in.readInt());
    setBlackElo(in.readInt());

    setEco1(in.readUTF());
    setEco2(in.readUTF());

    setWhiteFideId(in.readLong());
    setBlackFideId(in.readLong());
    setIncludeDateNull(in.readBoolean());
    setNbcoup1(in.readInt());
    setNbcoup2(in.readInt());
    setZobrist(in.readLong());

    setNbdamenoir(in.readInt());
    setNbpionnoir(in.readInt());
    setNbtournoir(in.readInt());
    setNbfounoir(in.readInt());
    setNbcavaliernoir(in.readInt());

    setNbdameblanc(in.readInt());
    setNbpionblanc(in.readInt());
    setNbtourblanc(in.readInt());
    setNbfoublanc(in.readInt());
    setNbcavalierblanc(in.readInt());

    setMaterialPrecis(in.readBoolean());

    setGrandRoqueBlanc(in.readBoolean());
    setGrandRoqueNoir(in.readBoolean());
    setPetitRoqueBlanc(in.readBoolean());
    setPetitRoqueNoir(in.readBoolean());

    setBxf7(in.readBoolean());
    setBxh6(in.readBoolean());
    setBxf2(in.readBoolean());
    setBxh3(in.readBoolean());
    setDoublecheck(in.readBoolean());
    setEp(in.readBoolean());
    setFoudecouleuropposee(in.readBoolean());
    setPat(in.readBoolean());
    setPionsdoubles(in.readBoolean());
    setPionstrples(in.readBoolean());


  }


  /**
   * TODO finir
   */
  public long maskFaitsDeJeu() {

    long value = 0L;
    if (isGrandRoqueBlanc) {
      value = value | FaitsDeJeuUtil.WHITE_CASTLE_LONG;
    }
    if (isPetitRoqueBlanc) {
      value = value | FaitsDeJeuUtil.WHITE_CASTLE_SHORT;
    }
    if (isGrandRoqueNoir) {
      value = value | FaitsDeJeuUtil.BLACK_CASTLE_LONG;
    }
    if (isPetitRoqueNoir) {
      value = value | FaitsDeJeuUtil.BLACK_CASTLE_SHORT;
    }

    if (isBxf7()) {
      value = value | FaitsDeJeuUtil.BXF7;
    }

    if (isBxf2()) {
      value = value | FaitsDeJeuUtil.BXF2;
    }
    if (isBxh6()) {
      value = value | FaitsDeJeuUtil.BXH6;
    }
    if (isBxh3()) {
      value = value | FaitsDeJeuUtil.BXH3;
    }

    if (isDoublecheck()) {
      value = value | FaitsDeJeuUtil.DOUBLECHECK;
    }

    if (isEp()) {
      value = value | FaitsDeJeuUtil.EP;
    }

    if (isFoudecouleuropposee()) {
      value = value | FaitsDeJeuUtil.OPPOSITEBISHOP;
    }

    if (isPat()) {
      value = value | FaitsDeJeuUtil.PAT;
    }

    if (isPionsdoubles()) {
      value = value | FaitsDeJeuUtil.DOUPLEPAWNS;
    }

    if (isPionstrples()) {
      value = value | FaitsDeJeuUtil.TRIPLEPAWNS;
    }

    return value;
  }

}
