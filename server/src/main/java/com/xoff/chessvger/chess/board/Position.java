package com.xoff.chessvger.chess.board;

import static com.xoff.chessvger.util.MaterialUtil.DEBUT_CAVALIER_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_CAVALIER_NOIR;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_DAME_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_DAME_NOIR;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_FOU_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_FOU_NOIR;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_PION_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_PION_NOIR;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_TOUR_BLANC;
import static com.xoff.chessvger.util.MaterialUtil.DEBUT_TOUR_NOIR;
import static com.xoff.chessvger.util.MaterialUtil.encode;

import com.xoff.chessvger.util.Zobrist;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
@Getter
@Setter
public class Position {

  private static final char SEPARATEUR_LIGNE_FEN = '/';
  private static final char SEPARATEUR_CHAMP_FEN = ' ';


  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private ListPiece listePiece = null;
  /**
   * a qui est ce de jouer ? BLANC false : noir
   */
  private char joueurAuTrait;
  /**
   * petit roque noir encore possible (ni le roi ni la tour n'ont bouge)
   */
  private boolean blackPetitRoque;
  /**
   * petit roque noir possible (ni le roi ni la tour n'ont bouge)
   */
  private boolean blackGrandRoque;
  /**
   * petit roque blanc possible (ni le roi ni la tour n'ont bouge)
   */
  private boolean whitePetitRoque;
  /**
   * grand roque blanc possible (ni le roi ni la tour n'ont bouge)
   */
  private boolean whiteGrandRoque;
  /**
   * nombre de demi coups depuis la derniere capture
   */
  private int nbDemiCoupsCapture;
  /**
   * nombre de coups de la partie
   */
  private int nbCoups;
  /**
   * destination possible pour prise en passant
   */
  private String destinationEnPassant;
  /**
   * la representation en byte de l'echiquier
   */
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private StringBuilder plateau;
  /**
   * nombre de pieces blanches restantes
   */
  private int nbblanches;
  /**
   * nombre de pieces noires restantes
   */
  private int nbnoires;
  /**
   * emplacement roi blanc
   */
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private Case caseRoiBlanc;
  /**
   * emplacement roi noir
   */
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private Case caseRoiNoir;
  /**
   * valeur issue de la BD
   */
  private Integer zobristValue = null;
  @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "We want that")
  private List<String> moves = null;
  private boolean hasWhiteRocked;
  private boolean hasBlackRocked;
  private String lastMove;

  private Integer key;

  public Position() {

    reset();
  }


  public static Position fen2Position(String fen) {

    Position result = new Position();
    //    System.out.println("origine :" + result);
    int taille = fen.length();
    int ligne = 7;
    int colonne = 0;
    int i = 0;
    for (; i < taille; i++) {
      //      System.out.println("etape :"+fen.charAt(i));
      switch (fen.charAt(i)) {
        case SEPARATEUR_LIGNE_FEN:

          ligne--;
          colonne = -1; //car on incremente apres ds la boucle
          // System.out.println("saut de ligne:" + ligne + "-->" + result);
          break;
        case '8':
        case '7':
        case '6':
        case '5':
        case '4':
        case '3':
        case '2':
        case '1':
          int initco = colonne;
          for (int j = colonne; j < Character.getNumericValue(fen.charAt(i)) + initco; j++) {
            result.setAt(j, ligne, MetierConstants.CASE_VIDE);
            colonne++;
          }
          //   System.out.println(colonne + " case vide : " + fen.charAt(i) + "--apres--" + result);
          colonne--;
          break;
        // noir
        case 'p':
          result.setAt(colonne, ligne, MetierConstants.PION_NOIR);
          break;
        case 'r':
          result.setAt(colonne, ligne, MetierConstants.TOUR_NOIR);
          break;
        case 'n':
          result.setAt(colonne, ligne, MetierConstants.CAVALIER_NOIR);
          break;
        case 'b':
          result.setAt(colonne, ligne, MetierConstants.FOU_NOIR);
          break;
        case 'q':
          result.setAt(colonne, ligne, MetierConstants.DAME_NOIR);
          break;
        case 'k':
          result.setAt(colonne, ligne, MetierConstants.ROI_NOIR);
          break;
        // blanc
        case 'P':
          result.setAt(colonne, ligne, MetierConstants.PION_BLANC);
          break;
        case 'R':
          result.setAt(colonne, ligne, MetierConstants.TOUR_BLANC);
          break;
        case 'N':
          result.setAt(colonne, ligne, MetierConstants.CAVALIER_BLANC);
          break;
        case 'B':
          result.setAt(colonne, ligne, MetierConstants.FOU_BLANC);
          break;
        case 'Q':
          result.setAt(colonne, ligne, MetierConstants.DAME_BLANC);
          break;
        case 'K':
          result.setAt(colonne, ligne, MetierConstants.ROI_BLANC);
          break;
        default:
          if (ligne == 0) {
            ligne = -1; // sortie rapide
          }
          break;
      } // end switch

      colonne++;

      if (ligne == -1) {
        //System.out.println("ligne = 0," + result);
        break;
      }
    }
    // maintenant on a les extra infos
    /*
          tour aux blancs (« w »)
          roques possibles (« KQkq »)
          case en passant (« - ») si on sait pas -
          comptage des coups (« 0 1 »)
    */
    String fin = fen.substring(i);
    StringTokenizer str = new StringTokenizer(fin, String.valueOf(SEPARATEUR_CHAMP_FEN));
    String joueurAuTrait = str.nextToken();
    if ("w".equals(joueurAuTrait)) {
      result.setJoueurAuTrait(MetierConstants.BLANC);
    } else {
      result.setJoueurAuTrait(MetierConstants.NOIR);
    }

    String roques = str.nextToken();
    result.setWhiteGrandRoque(roques.contains("Q"));
    result.setWhitePetitRoque(roques.contains("K"));
    result.setBlackGrandRoque(roques.contains("q"));
    result.setBlackPetitRoque(roques.contains("k"));
    String enpassant = str.nextToken();
    result.setLastMove(enpassant);
    String nbmoves = str.nextToken();
    result.setNbDemiCoupsCapture(Integer.parseInt(nbmoves));
    String nbmovestot = str.nextToken();
    result.setNbCoups(Integer.parseInt(nbmovestot));

    return result;
  }

  /**
   * affiche la liste des coups deja joues, utile pour debug
   */
  public String strListeMoves() {
    StringBuilder sb = new StringBuilder();
    for (String c : moves) {
      sb.append(c).append(" ");
    }
    return sb.toString();
  }

  /**
   * initialise la liste des piece
   */
  private void initListePiece() {
    setListePiece(new ListPiece());
    Piece roiBlanc = new Piece();
    roiBlanc.setActualPosition(new Case(4, 0, MetierConstants.ROI_BLANC));
    roiBlanc.setTypePiece(EnumPiece.ROI);
    getListePiece().addWhite(roiBlanc);
    Piece dameBlanc = new Piece();
    dameBlanc.setActualPosition(new Case(3, 0, MetierConstants.DAME_BLANC));
    dameBlanc.setTypePiece(EnumPiece.REINE);
    getListePiece().addWhite(dameBlanc);
    // CAVALIERS
    Piece cavalierBlanc1 = new Piece();
    cavalierBlanc1.setActualPosition(new Case(1, 0, MetierConstants.CAVALIER_BLANC));
    cavalierBlanc1.setTypePiece(EnumPiece.CAVALIER);
    getListePiece().addWhite(cavalierBlanc1);
    Piece cavalierBlanc2 = new Piece();
    cavalierBlanc2.setActualPosition(new Case(6, 0, MetierConstants.CAVALIER_BLANC));
    cavalierBlanc2.setTypePiece(EnumPiece.CAVALIER);
    getListePiece().addWhite(cavalierBlanc2);
    // FOUS
    Piece fouBlanc1 = new Piece();
    fouBlanc1.setActualPosition(new Case(2, 0, MetierConstants.FOU_BLANC));
    fouBlanc1.setTypePiece(EnumPiece.FOU);
    getListePiece().addWhite(fouBlanc1);
    Piece fouBlanc2 = new Piece();
    fouBlanc2.setActualPosition(new Case(5, 0, MetierConstants.FOU_BLANC));
    fouBlanc2.setTypePiece(EnumPiece.FOU);
    getListePiece().addWhite(fouBlanc2);
    // TOURS
    Piece tourBlanc1 = new Piece();
    tourBlanc1.setActualPosition(new Case(0, 0, MetierConstants.TOUR_BLANC));
    tourBlanc1.setTypePiece(EnumPiece.TOUR);
    getListePiece().addWhite(tourBlanc1);
    Piece tourBlanc2 = new Piece();
    tourBlanc2.setActualPosition(new Case(7, 0, MetierConstants.TOUR_BLANC));
    tourBlanc2.setTypePiece(EnumPiece.TOUR);
    getListePiece().addWhite(tourBlanc2);
    for (int i = 0; i < 8; i++) {
      Piece pionBlanc = new Piece();
      pionBlanc.setActualPosition(new Case(i, 1, MetierConstants.PION_BLANC));
      pionBlanc.setTypePiece(EnumPiece.PION);
      getListePiece().addWhite(pionBlanc);
    }
    // nOIR
    Piece tourNoir1 = new Piece();
    tourNoir1.setActualPosition(new Case(0, 7, MetierConstants.TOUR_NOIR));
    tourNoir1.setTypePiece(EnumPiece.TOUR);
    tourNoir1.setCouleur(MetierConstants.NOIR);
    getListePiece().addBlack(tourNoir1);
    Piece tourNoir2 = new Piece();
    tourNoir2.setActualPosition(new Case(7, 7, MetierConstants.TOUR_NOIR));
    tourNoir2.setTypePiece(EnumPiece.TOUR);
    tourNoir2.setCouleur(MetierConstants.NOIR);
    getListePiece().addBlack(tourNoir2);
    Piece roiNoir = new Piece();
    roiNoir.setActualPosition(new Case(4, 7, MetierConstants.ROI_NOIR));
    roiNoir.setTypePiece(EnumPiece.ROI);
    roiNoir.setCouleur(MetierConstants.NOIR);
    getListePiece().addBlack(roiNoir);
    Piece dameNoir = new Piece();
    dameNoir.setActualPosition(new Case(3, 7, MetierConstants.DAME_NOIR));
    dameNoir.setTypePiece(EnumPiece.REINE);
    dameNoir.setCouleur(MetierConstants.NOIR);
    getListePiece().addBlack(dameNoir);
    Piece cavalierNoir1 = new Piece();
    cavalierNoir1.setActualPosition(new Case(1, 7, MetierConstants.CAVALIER_NOIR));
    cavalierNoir1.setTypePiece(EnumPiece.CAVALIER);
    cavalierNoir1.setCouleur(MetierConstants.NOIR);
    getListePiece().addBlack(cavalierNoir1);
    Piece cavalierNoir2 = new Piece();
    cavalierNoir2.setActualPosition(new Case(6, 7, MetierConstants.CAVALIER_NOIR));
    cavalierNoir2.setTypePiece(EnumPiece.CAVALIER);
    cavalierNoir2.setCouleur(MetierConstants.NOIR);
    getListePiece().addBlack(cavalierNoir2);
    Piece fouNoir1 = new Piece();
    fouNoir1.setActualPosition(new Case(2, 7, MetierConstants.FOU_NOIR));
    fouNoir1.setTypePiece(EnumPiece.FOU);
    fouNoir1.setCouleur(MetierConstants.NOIR);
    getListePiece().addBlack(fouNoir1);
    Piece fouNoir2 = new Piece();
    fouNoir2.setActualPosition(new Case(5, 7, MetierConstants.FOU_NOIR));
    fouNoir2.setTypePiece(EnumPiece.FOU);
    fouNoir2.setCouleur(MetierConstants.NOIR);
    getListePiece().addBlack(fouNoir2);
    // ajout des pions
    for (int i = 0; i < 8; i++) {
      Piece pionNoir = new Piece();
      pionNoir.setActualPosition(new Case(i, 6, MetierConstants.PION_NOIR));
      pionNoir.setTypePiece(EnumPiece.PION);
      pionNoir.setCouleur(MetierConstants.NOIR);
      getListePiece().addBlack(pionNoir);
    }
  }


  public Position duplicate() {
    Position autre = new Position();
    autre.setListePiece(getListePiece().duplicate());
    autre.setJoueurAuTrait(joueurAuTrait);

    autre.setBlackPetitRoque(isBlackPetitRoque());

    autre.setBlackGrandRoque(isBlackGrandRoque());

    autre.setWhitePetitRoque(isWhitePetitRoque());

    autre.setWhiteGrandRoque(isWhiteGrandRoque());

    autre.setNbDemiCoupsCapture(getNbDemiCoupsCapture());

    autre.setNbCoups(getNbCoups());

    autre.setDestinationEnPassant(getDestinationEnPassant());

    autre.setPlateau(new StringBuilder(getPlateau()));

    autre.setNbblanches(getNbblanches());

    autre.setNbnoires(getNbnoires());

    autre.setCaseRoiBlanc(caseRoiBlanc.duplicate());

    autre.setCaseRoiNoir(caseRoiNoir.duplicate());

    autre.setZobristValue(getZobristValue());
    for (String s : moves) {
      autre.getMoves().add(s);
    }
    autre.setHasWhiteRocked(hasWhiteRocked);
    autre.setHasBlackRocked(hasBlackRocked);
    autre.setLastMove(lastMove);
    autre.setKey(getKey());
    return autre;
  }


  /**
   * fait un clear sur la position en ne laissant que les 2 rois
   */
  public void reset() {
    moves = new ArrayList<>();
    plateau = new StringBuilder(MetierConstants.INIT_BOARD);
    nbblanches = 16;
    nbnoires = 16;
    joueurAuTrait = MetierConstants.BLANC;
    blackPetitRoque = true;
    blackGrandRoque = true;
    whiteGrandRoque = true;
    whitePetitRoque = true;
    hasBlackRocked = false;
    hasWhiteRocked = false;
    destinationEnPassant = MetierConstants.EMPTY;
    nbCoups = 0;
    nbDemiCoupsCapture = 0;
    caseRoiBlanc = new Case(4, 0, MetierConstants.ROI_BLANC);
    caseRoiNoir = new Case(4, 7, MetierConstants.ROI_NOIR);
    lastMove = MetierConstants.EMPTY;
    initListePiece();
  }


  public boolean verify() {
    // TODO
    //(2 rois, nb piece> 16), nb reines>9

    if (nbblanches > 16) {
      return false;
    }
    if (nbnoires > 16) {
      return false;
    }
    if (!checkBlanc()) {
      return checkBlack();
    }
    return true;

  }

  private boolean checkBlack() {
    // voir pour les noirs + cas impossibles FIXME

    List<Piece> pieces = getListePiece().getBlackPieces();
    int nbroi = 0;
    int nbdame = 0;
    int nbtour = 0;
    int nbfou = 0;
    int nbcavalier = 0;
    int nbpion = 0;
    for (Piece piece : pieces) {
      switch (piece.getTypePiece()) {
        case PION:
          nbpion++;
          break;
        case CAVALIER:
          nbcavalier++;
          break;
        case FOU:
          nbfou++;
          break;
        case TOUR:
          nbtour++;
          break;
        case REINE:
          nbdame++;
          break;
        case ROI:
          nbroi++;
          break;
        default:
      }
    }
    return nbroi <= 1 && nbcavalier <= 10 && nbfou <= 10 && nbtour <= 10 && nbdame <= 9 &&
        nbpion <= 8;

  }

  private boolean checkBlanc() {
    List<Piece> pieces = getListePiece().getWhitePieces();
    int nbroi = 0;
    int nbdame = 0;
    int nbtour = 0;
    int nbfou = 0;
    int nbcavalier = 0;
    int nbpion = 0;
    for (Piece piece : pieces) {
      switch (piece.getTypePiece()) {
        case PION:
          nbpion++;
          break;
        case CAVALIER:
          nbcavalier++;
          break;
        case FOU:
          nbfou++;
          break;
        case TOUR:
          nbtour++;
          break;
        case REINE:
          nbdame++;
          break;
        case ROI:
          nbroi++;
          break;
        default:
      }
    }
    return nbroi <= 1 && nbcavalier <= 10 && nbfou <= 10 && nbtour <= 10 && nbdame <= 9 &&
        nbpion <= 8;
  }

  /**
   * renvoie une representation d'une position
   */
  @Override
  public String toString() {
    StringBuilder res = new StringBuilder(200);
    res.append("-").append(nbCoups);

    if (joueurAuTrait == MetierConstants.BLANC) {
      res.append("BLANC-");
    } else {
      res.append("NOIR-");
    }
    // roque
    /*
     res.append(whitePetitRoque);
     res.append(whiteGrandRoque);
     res.append(blackPetitRoque);
     res.append(blackGrandRoque);
     res.append("-").append(destinationEnPassant);

     res.append("-").append(nbDemiCoupsCapture);
     res.append("-").append(getCaseRoiBlanc());
     res.append("-").append(getCaseRoiNoir());
     */
    res.append("<br>");
    int l;
    int c;
    for (l = 7; l > -1; l--) {
      res.append("\n");
      for (c = 0; c < 8; c++) {
        res.append("|");
        res.append(getAt(c, l));
        res.append(" ");
      }
    } // fin ligne
    res.append("\n");
    //res.append(getListePiece());
    /*
    for (String s:getListeMoves()){
    res.append(s).append(" ");
    }*/
    return res.toString();
  }

  public String tofen() {

    StringBuilder res = new StringBuilder(200);
    // les pieces
    for (int l = 7; l > -1; l--) {
      for (int c = 0; c < 8; c++) {
        int nbvide = 0;
        while (c < 8 && getAt(c, l) == MetierConstants.CASE_VIDE) {
          nbvide++;
          c++;
        }
        if (nbvide > 0) {
          res.append(nbvide);
        }
        if (c < 8 && getAt(c, l) != MetierConstants.CASE_VIDE) {
          res.append(getAt(c, l));
        }
      }
      if (l != 0) {
        res.append(SEPARATEUR_LIGNE_FEN);
      } else {
        res.append(SEPARATEUR_CHAMP_FEN);
      }
    } // fin ligne
    //res.append(" ");
    if (joueurAuTrait == MetierConstants.BLANC) {
      res.append("w");
    } else {
      res.append("b");
    }
    res.append(SEPARATEUR_CHAMP_FEN);
    StringBuilder subres = new StringBuilder();
    // les roques
    if (isWhitePetitRoque()) {
      subres.append("K");
    }
    if (isWhiteGrandRoque()) {
      subres.append("Q");
    }
    if (isBlackPetitRoque()) {
      subres.append("k");
    }
    if (isBlackGrandRoque()) {
      subres.append("q");
    }
    if (subres.length() == 0) {
      subres.append("-");
    }
    res.append(subres);
    res.append(SEPARATEUR_CHAMP_FEN);
    // en passant
    if (StringUtils.isEmpty(destinationEnPassant)) {
      res.append("-");
    } else {
      res.append(destinationEnPassant);
    }
    res.append(SEPARATEUR_CHAMP_FEN);

    // coup
    res.append(nbDemiCoupsCapture);
    res.append(SEPARATEUR_CHAMP_FEN);
    res.append(nbCoups);
    return res.toString();
  }

  /**
   * fait un clear sur la position en ne laissant que les 2 rois
   */
  public void clear() {
    plateau = new StringBuilder(MetierConstants.INIT_BOARD);
    joueurAuTrait = MetierConstants.BLANC;
    blackPetitRoque = true;
    blackGrandRoque = true;
    whiteGrandRoque = true;
    whitePetitRoque = true;
    destinationEnPassant = MetierConstants.EMPTY;
    nbCoups = 0;
    nbDemiCoupsCapture = 0;
    hasBlackRocked = false;
    hasWhiteRocked = false;
    setAt(4, 7, MetierConstants.ROI_NOIR);
    setAt(4, 0, MetierConstants.ROI_BLANC);
    caseRoiBlanc = new Case(4, 0, MetierConstants.ROI_BLANC);
    caseRoiNoir = new Case(4, 7, MetierConstants.ROI_NOIR);
    setListePiece(new ListPiece());
    // les 2 rois ds la liste des pieces
    Piece roiBlanc = new Piece();
    roiBlanc.setActualPosition(new Case(4, 0, MetierConstants.ROI_BLANC));
    roiBlanc.setTypePiece(EnumPiece.ROI);
    getListePiece().addWhite(roiBlanc);
    Piece roiNoir = new Piece();
    roiNoir.setActualPosition(new Case(4, 7, MetierConstants.ROI_NOIR));
    roiNoir.setTypePiece(EnumPiece.ROI);
    roiNoir.setCouleur(MetierConstants.NOIR);
    getListePiece().addBlack(roiNoir);
    moves.clear();
  }

  /**
   * setAt en 0,0 on a en bas à gauche, en 8,8 en haut à droite
   */
  public void setAt(int colonne, int ligne, char piece) {
    try {
      plateau.setCharAt((ligne * 8) + colonne, piece);
    } catch (Exception e) {
      log.error("setAt:c=" + colonne + ",l=" + ligne, e);
    }
  }

  /**
   * setAt copie la piece en case origine dans la case destination
   */
  public void setAt(Case origine, Case destination) {
    setAt(destination.column, destination.raw, getAt(origine.column, origine.raw));
  }

  /**
   * setAt copie la piece en case origine dans la case destination
   */
  public void setAt(Case destination) {
    setAt(destination.column, destination.raw, destination.pieceOnTheBoard);
  }

  /**
   * setAt copie la piece dans la case destination
   */
  public void setAt(Case destination, char piece) {
    setAt(destination.column, destination.raw, piece);
  }

  public char getAt(int colonne, int ligne) {
    return plateau.charAt((ligne << 3) + colonne);
  }


  public int getSens() {
    if (joueurAuTrait == MetierConstants.BLANC) {
      return -1;
    } else {
      return 1;
    }
  }

  public CoupleZobristMaterial evaluateMaterial() {
    int resultat = 0;
    int value = 0;
    int nbpionnoir = 0;
    int nbpionblanc = 0;
    int nbtournoir = 0;
    int nbtourblanc = 0;
    int nbcavalierblanc = 0;
    int nbcavaliernoir = 0;
    int nbfounoir = 0;
    int nbfoublanc = 0;
    int nbdamenoir = 0;
    int nbdameblanc = 0;
    for (int i = 0; i < MetierConstants.SIZE; i++) {
      char caset = plateau.charAt(i);
      switch (caset) {
        case MetierConstants.CASE_VIDE:
          break;
        case MetierConstants.PION_BLANC:
          nbpionblanc++;
          break;
        case MetierConstants.TOUR_BLANC:
          nbtourblanc++;
          break;
        case MetierConstants.CAVALIER_BLANC:
          nbcavalierblanc++;
          break;
        case MetierConstants.FOU_BLANC:
          nbfoublanc++;
          break;
        case MetierConstants.ROI_BLANC:
          break;
        case MetierConstants.DAME_BLANC:
          nbdameblanc++;
          break;
        case MetierConstants.PION_NOIR:
          nbpionnoir++;
          break;
        case MetierConstants.TOUR_NOIR:
          nbtournoir++;
          break;
        case MetierConstants.CAVALIER_NOIR:
          nbcavaliernoir++;
          break;
        case MetierConstants.FOU_NOIR:
          nbfounoir++;
          break;
        case MetierConstants.ROI_NOIR:
          break;
        case MetierConstants.DAME_NOIR:
          nbdamenoir++;
          break;
        default:
          log.error("unknown case-Position.evaluate " + caset);
      }
      resultat = resultat ^ value;
    }
    // @INFO idealement il faudrait verifier les nb, <8 pion et 6 pour le reste
    long materialValue = 0L;
    materialValue = encode(materialValue, nbpionblanc, DEBUT_PION_BLANC);
    materialValue |= encode(materialValue, nbpionnoir, DEBUT_PION_NOIR);
    materialValue |= encode(materialValue, nbcavalierblanc, DEBUT_CAVALIER_BLANC);
    materialValue |= encode(materialValue, nbcavaliernoir, DEBUT_CAVALIER_NOIR);
    materialValue |= encode(materialValue, nbfoublanc, DEBUT_FOU_BLANC);
    materialValue |= encode(materialValue, nbfounoir, DEBUT_FOU_NOIR);
    materialValue |= encode(materialValue, nbdameblanc, DEBUT_DAME_BLANC);
    materialValue |= encode(materialValue, nbdamenoir, DEBUT_DAME_NOIR);
    materialValue |= encode(materialValue, nbtourblanc, DEBUT_TOUR_BLANC);
    materialValue |= encode(materialValue, nbtournoir, DEBUT_TOUR_NOIR);
    return new CoupleZobristMaterial(0, materialValue);
  }


  public CoupleZobristMaterial evaluateZobristAndMaterial() {
    int resultat = 0;
    int value = 0;
    int nbpionnoir = 0;
    int nbpionblanc = 0;
    int nbtournoir = 0;
    int nbtourblanc = 0;
    int nbcavalierblanc = 0;
    int nbcavaliernoir = 0;
    int nbfounoir = 0;
    int nbfoublanc = 0;
    int nbdamenoir = 0;
    int nbdameblanc = 0;
    for (int i = 0; i < MetierConstants.SIZE; i++) {
      char caset = plateau.charAt(i);
      switch (caset) {
        case MetierConstants.CASE_VIDE:
          value = Zobrist.getTAlea()[Zobrist.CODE_ZOBRIST_NULL][0][i];
          break;
        case MetierConstants.PION_BLANC:
          value = Zobrist.getTAlea()[Zobrist.CODE_ZOBRIST_PION][Zobrist.CODE_ZOBRIST_BLANC][i];
          nbpionblanc++;
          break;
        case MetierConstants.TOUR_BLANC:
          value = Zobrist.getTAlea()[Zobrist.CODE_ZOBRIST_TOUR][Zobrist.CODE_ZOBRIST_BLANC][i];
          nbtourblanc++;
          break;
        case MetierConstants.CAVALIER_BLANC:
          value = Zobrist.getTAlea()[Zobrist.CODE_ZOBRIST_CAVALIER][Zobrist.CODE_ZOBRIST_BLANC][i];
          nbcavalierblanc++;
          break;
        case MetierConstants.FOU_BLANC:
          value = Zobrist.getTAlea()[Zobrist.CODE_ZOBRIST_FOU][Zobrist.CODE_ZOBRIST_BLANC][i];
          nbfoublanc++;
          break;
        case MetierConstants.ROI_BLANC:
          value = Zobrist.getTAlea()[Zobrist.CODE_ZOBRIST_ROI][Zobrist.CODE_ZOBRIST_BLANC][i];
          break;
        case MetierConstants.DAME_BLANC:
          value = Zobrist.getTAlea()[Zobrist.CODE_ZOBRIST_REINE][Zobrist.CODE_ZOBRIST_BLANC][i];
          nbdameblanc++;
          break;
        case MetierConstants.PION_NOIR:
          value = Zobrist.getTAlea()[Zobrist.CODE_ZOBRIST_PION][Zobrist.CODE_ZOBRIST_NOIR][i];
          nbpionnoir++;
          break;
        case MetierConstants.TOUR_NOIR:
          value = Zobrist.getTAlea()[Zobrist.CODE_ZOBRIST_TOUR][Zobrist.CODE_ZOBRIST_NOIR][i];
          nbtournoir++;
          break;
        case MetierConstants.CAVALIER_NOIR:
          value = Zobrist.getTAlea()[Zobrist.CODE_ZOBRIST_CAVALIER][Zobrist.CODE_ZOBRIST_NOIR][i];
          nbcavaliernoir++;
          break;
        case MetierConstants.FOU_NOIR:
          value = Zobrist.getTAlea()[Zobrist.CODE_ZOBRIST_FOU][Zobrist.CODE_ZOBRIST_NOIR][i];
          nbfounoir++;
          break;
        case MetierConstants.ROI_NOIR:
          value = Zobrist.getTAlea()[Zobrist.CODE_ZOBRIST_ROI][Zobrist.CODE_ZOBRIST_NOIR][i];
          break;
        case MetierConstants.DAME_NOIR:
          value = Zobrist.getTAlea()[Zobrist.CODE_ZOBRIST_REINE][Zobrist.CODE_ZOBRIST_NOIR][i];
          nbdamenoir++;
          break;
        default:
          log.error(" erreur evaluateZobristAndMaterial " + caset);
      }

      resultat = resultat ^ value;
    }
    if (joueurAuTrait == MetierConstants.NOIR) {
      resultat = resultat ^ Zobrist.CODE_ZOBRIST_NOIR_AU_TRAIT;
    }
    setZobristValue(resultat);
    // @INFO idealement il faudrait verifier les nb, <8 pion et 6 pour le reste
    long materialValue = 0L;
    materialValue = encode(materialValue, nbpionblanc, DEBUT_PION_BLANC);
    materialValue |= encode(materialValue, nbpionnoir, DEBUT_PION_NOIR);
    materialValue |= encode(materialValue, nbcavalierblanc, DEBUT_CAVALIER_BLANC);
    materialValue |= encode(materialValue, nbcavaliernoir, DEBUT_CAVALIER_NOIR);
    materialValue |= encode(materialValue, nbfoublanc, DEBUT_FOU_BLANC);
    materialValue |= encode(materialValue, nbfounoir, DEBUT_FOU_NOIR);
    materialValue |= encode(materialValue, nbdameblanc, DEBUT_DAME_BLANC);
    materialValue |= encode(materialValue, nbdamenoir, DEBUT_DAME_NOIR);
    materialValue |= encode(materialValue, nbtourblanc, DEBUT_TOUR_BLANC);
    materialValue |= encode(materialValue, nbtournoir, DEBUT_TOUR_NOIR);
    return new CoupleZobristMaterial(getZobristValue(), materialValue);
  }


  @Override
  public boolean equals(Object o) {

    if (o == this) {
      return true;
    }
    if (!(o instanceof Position other)) {
      return false;
    }

    boolean b = (evaluateZobristAndMaterial() == other.evaluateZobristAndMaterial());
    log.info("equals position :" + b);
    return b;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 43 * hash + this.nbCoups;
    hash = 43 * hash + this.nbblanches;
    hash = 43 * hash + this.nbnoires;
    return hash;
  }

  public StringBuilder getSpecificitesPosition() {
    StringBuilder info = new StringBuilder(8);
    info.append(blackPetitRoque ? 't' : 'f');
    info.append(blackGrandRoque ? 't' : 'f');
    info.append(whiteGrandRoque ? 't' : 'f');
    info.append(whitePetitRoque ? 't' : 'f');
    info.append(destinationEnPassant).append(":");
    info.append(nbCoups).append(":");
    info.append(nbDemiCoupsCapture).append(":");
    if (getNbblanches() < 10) {
      info.append("0");
    }
    info.append(getNbblanches());
    if (getNbnoires() < 10) {
      info.append("0");
    }
    info.append(getNbnoires());
    info.append(getJoueurAuTrait());
    return info;
  }


}
