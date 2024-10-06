package com.xoff.chessvger.chess.game;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xoff.chessvger.chess.player.CommonPlayer;
import com.xoff.chessvger.chess.player.ICommonPlayerManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.util.Constants;
import com.xoff.chessvger.util.PgnUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Slf4j
@Data
// TODO une partie doit bouger vers un view game comme getTitle par exemple, quid des getter et setter ?
public class CommonGame implements Serializable {
  @Serial
  private static final long serialVersionUID = 3199296740111706113L;


  private long id;
  private String event;
  private String site;
  private boolean partieAnalysee;
  // TODO mettre des longs a la place
  private String date;
  private String eventDate;

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

  private MetaCommonGame metaCommonGame;


  public CommonGame() {

    isDeleted = false;
    event = StringUtils.EMPTY;
    site = StringUtils.EMPTY;
    date = StringUtils.EMPTY;
    round = StringUtils.EMPTY;
    result =StringUtils.EMPTY;
    whiteTitle = StringUtils.EMPTY;
    blackTitle = StringUtils.EMPTY;
    whiteElo = 0;
    blackElo = 0;
    eco = StringUtils.EMPTY;
    opening = StringUtils.EMPTY;
    whiteFideId = 0L;
    blackFideId = 0L;
    eventDate = StringUtils.EMPTY;
    nbcoups = 0;
    lastPosition = 0;
    informationsFaitDeJeu = 0;
    lastUpdate = 0;
    moves = " ";


    metaCommonGame=new MetaCommonGame();
    partieAnalysee = false;

    interet = Constants.NON_SIGNIFICATIVE_INT;
    theorique = false;
    favori = false;
  }

  private void writeObject(ObjectOutputStream out) throws IOException {

    out.writeLong(getId());
    out.writeBoolean(isPartieAnalysee());
    out.writeBoolean(isDeleted());
    out.writeLong(getLastUpdate());

    out.writeUTF(getFirstMove());
    out.writeUTF(getEvent());

    out.writeUTF(getSite());
    out.writeUTF(getDate());
    out.writeUTF(getRound());

    out.writeUTF(getResult());
    out.writeUTF(getWhiteTitle());
    out.writeUTF(getBlackTitle());

    out.writeInt(getWhiteElo());
    out.writeInt(getBlackElo());

    out.writeUTF(getEco());
    out.writeUTF(getOpening());

    out.writeLong(getWhiteFideId());
    out.writeLong(getBlackFideId());

    out.writeUTF(getEventDate());
    out.writeInt(getNbcoups());
    out.writeLong(getLastPosition());
    out.writeLong(getInformationsFaitDeJeu());
    out.writeUTF(getMoves());

    out.writeInt(getInteret());
    out.writeBoolean(isTheorique());
    out.writeBoolean(isFavori());

    out.writeUTF(GlobalManager.getInstance().getObjectMapper().writeValueAsString(metaCommonGame));

  }

  private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

    setId(in.readLong());
    setPartieAnalysee(in.readBoolean());
    setDeleted(in.readBoolean());
    setLastUpdate(in.readLong());

    setFirstMove(in.readUTF());
    setEvent(in.readUTF());
    setSite(in.readUTF());
    setDate(in.readUTF());
    setRound(in.readUTF());
    setResult(in.readUTF());
    setWhiteTitle(in.readUTF());
    setBlackTitle(in.readUTF());
    setWhiteElo(in.readInt());
    setBlackElo(in.readInt());
    setEco(in.readUTF());
    setOpening(in.readUTF());
    setWhiteFideId(in.readLong());
    setBlackFideId(in.readLong());
    setEventDate(in.readUTF());

    setNbcoups(in.readInt());
    setLastPosition(in.readLong());

    setInformationsFaitDeJeu(in.readLong());
    setMoves(in.readUTF());
    setInteret(in.readInt());
    setTheorique(in.readBoolean());
    setFavori(in.readBoolean());
    String json=in.readUTF();
    MetaCommonGame metaCommonGame1 = GlobalManager.getInstance().getObjectMapper().readValue(json, MetaCommonGame.class);
  setMetaCommonGame(metaCommonGame1);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    CommonGame autre = (CommonGame) obj;
    //  return (getNbcoups() == autre.getNbcoups() && getLastPosition()
    //  == autre.getLastPosition() && autre.getWhiteFideID()
    //  == (getWhiteFideID()) && getBlackFideID() == (autre.getBlackFideID()));
    return getMoves().equals(autre.getMoves());
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37).append(id).append(getMoves()).toHashCode();
  }
  @Override
  public String toString() {


    String sb = "{id=" + getId() + "{premier=" + getFirstMove() + ",site=" + getSite() + ",round=" +
        getRound() + ",date=" + getDate() + ",event=" + getEvent() + ",eco=" + getEco() +
        ",result=" + getResult() + ",blanc=" + getWhiteFideId() + "/" + getNomBlanc() + ",/=" +
        getWhiteTitle() + ",noir=" + getBlackFideId() + "/" + getNomNoir() + ",/=" +
        getBlackTitle()  + ",isd=" + isDeleted() + "{}" + "{ai=" + moves  + "{filtresSupplementaires=" + getInteret() + "," + isTheorique() + "," + isFavori() +
        "}" + "{getMetaCommonGame=" + getMetaCommonGame() + "}";
    return sb;
  }


  public boolean isDoublon(CommonGame item) {
    return this.equals(item);
  }

  /**
   * [Event "89th Hastings Masters 2013-14"] [Site "Hastings ENG"] [Date
   * "2013.12.31"] [Round "4.1"] [White "Ma Qun"] [Black "Gormally,D"] [Result
   * "1/2-1/2"] [WhiteTitle "GM"] [BlackTitle "GM"] [WhiteElo "2595"]
   * [BlackElo "2500"] [ECO "B90"] [Opening "Sicilian"] [Variation "Najdorf,
   * Byrne (English) attack"] [WhiteFideId "8603154"] [BlackFideId "406465"]
   * [EventDate "2013.12.28"]
   */
  public String toPGN() {
    StringBuilder sb = new StringBuilder();
    sb.append("[Event \"").append(getEvent()).append("\"]").append(System.lineSeparator());
    sb.append("[EventDate \"").append(getEventDate()).append("\"]").append(System.lineSeparator());
    sb.append("[Site  \"").append(getSite()).append("\"]").append(System.lineSeparator());
    sb.append("[Date  \"").append(getDate()).append("\"]").append(System.lineSeparator());
    sb.append("[Round  \"").append(getRound()).append("\"]").append(System.lineSeparator());
    sb.append("[White  \"").append(getNomBlanc()).append("\"]").append(System.lineSeparator());
    sb.append("[Black  \"").append(getNomNoir()).append("\"]").append(System.lineSeparator());
    sb.append("[Result  \"").append(getResult()).append("\"]").append(System.lineSeparator());
    sb.append("[WhiteTitle  \"").append(getWhiteTitle()).append("\"]")
        .append(System.lineSeparator());
    sb.append("[BlackTitle  \"").append(getBlackTitle()).append("\"]")
        .append(System.lineSeparator());

    sb.append("[WhiteElo  \"").append(getWhiteElo()).append("\"]").append(System.lineSeparator());
    sb.append("[BlackElo  \"").append(getBlackElo()).append("\"]").append(System.lineSeparator());

    sb.append("[ECO  \"").append(getEco()).append("\"]").append(System.lineSeparator());
    sb.append("[Opening  \"").append(getOpening()).append("\"]").append(System.lineSeparator());
    if (getWhiteFideId() < Constants.ID_MIN_NON_FIDE_PLAYER) {
      sb.append("[WhiteFideId  \"").append(getWhiteFideId()).append("\"]")
          .append(System.lineSeparator());
    }
    if (getBlackFideId() < Constants.ID_MIN_NON_FIDE_PLAYER) {

      sb.append("[BlackFideId  \"").append(getBlackFideId()).append("\"]")
          .append(System.lineSeparator());
    }
    sb.append("\r\n").append(System.lineSeparator());
    sb.append(getMoves()).append(System.lineSeparator());
    sb.append(" ").append(getResult()).append(System.lineSeparator());
    return sb.toString();
  }

  public CommonGame duplicate() {
    CommonGame autre = new CommonGame();
    autre.setId(getId());
    autre.setEvent(getEvent());
    autre.setSite(getSite());
    autre.setDate(getDate());
    autre.setRound(getRound());
    autre.setResult(getResult());
    autre.setWhiteTitle(getWhiteTitle());
    autre.setBlackTitle(getBlackTitle());
    autre.setWhiteElo(getWhiteElo());
    autre.setBlackElo(getBlackElo());
    autre.setEco(getEco());
    autre.setOpening(getOpening());
    autre.setWhiteFideId(getWhiteFideId());
    autre.setBlackFideId(getBlackFideId());
    autre.setEventDate(getEventDate());
    autre.setPartieAnalysee(partieAnalysee);
    autre.setNbcoups(getNbcoups());
    autre.setLastPosition(getLastPosition());
    autre.setInformationsFaitDeJeu(getInformationsFaitDeJeu());

    autre.setLastUpdate(getLastUpdate());

    autre.setDeleted(isDeleted());

    autre.setMoves(getMoves());
    autre.setMetaCommonGame(getMetaCommonGame().duplicate());
    autre.setInteret(getInteret()); // non defini
    autre.setTheorique(isTheorique());
    autre.setFavori(isFavori());
    autre.setFirstMove(getFirstMove());
    return autre;
  }


@JsonIgnore
  public String getNomBlanc() {
    //@FIXME
    ICommonPlayerManager commonPlayerManager = GlobalManager.getInstance().getCommonPlayerManager();
    CommonPlayer p1 = commonPlayerManager.findById(getWhiteFideId());
    if (p1 != null) {
      return p1.getName();
    }
    return "*";
  }

  @JsonIgnore
  public String getNomNoir() {

    //@FIXME
    ICommonPlayerManager commonPlayerManager = GlobalManager.getInstance().getCommonPlayerManager();
    CommonPlayer p1 = commonPlayerManager.findById(getBlackFideId());

    if (p1 != null) {
      return p1.getName();
    }
    return "*";
  }



}
