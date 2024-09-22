package com.xoff.chessvger.util;

import com.xoff.chessvger.chess.database.DBOperation;
import com.xoff.chessvger.chess.database.DatabaseManager;
import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.chess.player.CommonPlayer;
import com.xoff.chessvger.common.GlobalManager;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j

public class Parser {

  public Parser() {

  }


  public String readPgnFile(String emplacement) {
    StringBuilder result = new StringBuilder();
    try {
      // FIXME on fait qu un fichier et pas tout le repertoire
      log.info("pgn file: " + emplacement);
      List<String> lines = Files.readAllLines(Paths.get(emplacement), StandardCharsets.ISO_8859_1);
      lines.forEach(ligne -> {
        ligne = ligne.replace("\ufeff", ""); // ajout pour palier bug BOM
        ligne = ligne.replace("0.", "0. ");
        ligne = ligne.replace("1.", "1. ");
        ligne = ligne.replace("2.", "2. ");
        ligne = ligne.replace("3.", "3. ");
        ligne = ligne.replace("4.", "4. ");
        ligne = ligne.replace("5.", "5. ");
        ligne = ligne.replace("6.", "6. ");
        ligne = ligne.replace("7.", "7. ");
        ligne = ligne.replace("8.", "8. ");
        ligne = ligne.replace("9.", "9. ");
        ligne = ligne.replace("  ", " "); // fichier pgn lichess
        result.append(ligne).append(" ");
      });
    } catch (IOException e) {
      log.error(e.getMessage());
    }
    return result.toString();
  }

  public int parseData(String data, DatabaseManager databaseManager, String fileName) {
    log.info("parse data  " + fileName);
    int etat = 0;
    int len = data.length();
    int compteur = 0;
    int nbgame = 0;

    CommonGame game = new CommonGame();
    boolean toAdd = true;
    game.getMetaCommonGame().setSource(fileName);
    while (compteur < len) {
      switch (data.charAt(compteur)) {
        case '[':
          etat = 1;
          StringBuilder sbt = new StringBuilder();
          for (int j = compteur + 1; j < data.length() - 1; j++) {
            if (data.charAt(j) == ' ' && data.charAt(j + 1) == '"') {
              break;
            }
            sbt.append(data.charAt(j));
          }
          String token = sbt.toString();
          StringBuilder sbtvalue = new StringBuilder();
          int deb = compteur + 1 + token.length() + 2;
          for (int j = deb; j < data.length() - 1; j++) {
            if (data.charAt(j) == '"' && data.charAt(j + 1) == ']') {
              break;
            }
            sbtvalue.append(data.charAt(j));
          }
          String value = sbtvalue.toString();
          //  log.info("value="+value);
          switch (token) {
            case "Event":
              game.setEvent(value);
              break;
            case "Site":
              game.setSite(value);
              break;
            case "Date":
              game.setDate(value);
              break;
            case "EventDate":
              game.setEventDate(value);
              break;
            case "Round":
              game.setRound(value);
              break;
            case "WhiteTitle":
              game.setWhiteTitle(value);
              break;
            case "BlackTitle":
              game.setBlackTitle(value);
              break;
            case "WhiteElo":
              game.setWhiteElo(PgnUtil.interpreteValue(value));
              break;
            case "BlackElo":
              game.setBlackElo(PgnUtil.interpreteValue(value));
              break;
            case "ECO":
              game.setEco(value);
              break;
            case "Opening":
              game.setOpening(value);
              break;
            case "WhiteFideId":
              game.setWhiteFideId(Long.parseLong(value));
              break;
            case "BlackFideId":
              game.setBlackFideId(Long.parseLong(value));
              break;
            case "Result":
              game.setResult(value);
              break;
            case "White":
              CommonPlayer p1 = GlobalManager.getInstance().getCommonPlayerManager()
                  .findOrAdd(value, game.getWhiteFideId());
              game.setWhiteFideId(p1.getIdnumber());
              databaseManager.getPlayerOfDbManager().add(p1.getIdnumber());
              break;
            case "Black":
              CommonPlayer p2 = GlobalManager.getInstance().getCommonPlayerManager()
                  .findOrAdd(value, game.getBlackFideId());
              game.setBlackFideId(p2.getIdnumber());
              databaseManager.getPlayerOfDbManager().add(p2.getIdnumber());
              break;
            case "Variant":
              if ("standard".equalsIgnoreCase(value)) {
                // cas lichess
              }
              break;
            case "Variation":
            case "EventType":
            case "BlackTeam":
            case "WhiteTeam":
            case "BlackTeamCountry":
            case "WhiteTeamCountry":
            case "EventCountry":
            case "EventCategory":
            case "PlyCount":
            case "EventRounds":  // FIXME mettre tout ca en meta info
              break;
            default:
              log.info("Parser switch " + token + ":'" + value);
          }
          break;
        //
        case ']':
          if (etat == 1) {
            etat = 2;
          }
          break;
        case '1':
          // on fait sauter les parties sans coup
          if (etat == 2 && data.charAt(compteur + 1) == '.') {
            StringBuilder sbmoves = new StringBuilder();

            //         log.info("resultat=" + resultat);
            for (int debut = compteur; debut < len; debut++) {
              boolean isResultat = true;
              for (int i = 0; i < game.getResult().length(); i++) {
                if (game.getResult().charAt(i) != data.charAt(debut + i)) {
                  isResultat = false;
                  break;
                }
              }
              if (isResultat) {
                break;
              }
              sbmoves.append(data.charAt(debut));
            }
            String coups = sbmoves.toString();
            // log.info("moves=" + coups);
            // on positionnera le game ID dans le game DB à l'ajout du additonnal
            game.setMoves(coups);
            game.getMetaCommonGame().setSource(fileName);
            int debut = coups.indexOf("1. ") + 3;
            //  log.info(fileName+"***moves=" + coups);
            // log.info(game+"***moves=" + coups);
            coups = coups.substring(debut);
            /// dans certains cas on a encore un espace au debut
            coups = coups.replaceAll("^\\s+", "");
            // TODO gerer les commentaires bien sur
            int fin = coups.indexOf(" ");
            //     log.info("coups.substring(debut)::" + coups);
            game.setFirstMove(coups.substring(0, fin));
            int min = 0;
            int delta = game.getResult().length();
            compteur = compteur + min + delta;
            //log.info("moves=" + coups);
            etat = 0;
            if (toAdd) {
              nbgame++;

              databaseManager.upsert(game, DBOperation.AJOUT);

            }
            game = new CommonGame();
            toAdd = true;
            game.getMetaCommonGame().setSource(fileName);
          }
          break;
        default:
          break;
      }
      compteur++;
    }
    return nbgame;
  }


  public int parseDir(File dir, DatabaseManager databaseManager) {
    log.info("parseDir A :" + dir.getAbsolutePath());
    File[] files = dir.listFiles();
    if (files == null) {
      return 0;
    }
    int total = 0;
    for (File file : files) {
      log.info("parseDir :" + file.getAbsolutePath());
      if (file.isDirectory()) {
        log.info("sous rep:" + file.getAbsolutePath());
        total += parseDir(file, databaseManager);
      } else {
        if (file.getAbsolutePath().toLowerCase().endsWith(Constants.PGN_FILE)) {
          total += parseData(readPgnFile(file.getAbsolutePath()), databaseManager, file.getName());
        } else if (file.getAbsolutePath().toLowerCase().endsWith(Constants.ZIP_FILE)) {
          try {
            total += ZipUtil.traiteZipFile(file.getAbsolutePath(), databaseManager, this);
          } catch (IOException ioe) {
            log.error("erreur unzip:" + file.getAbsolutePath(), ioe);
          }
        }
      }
    }
    return total;
  }

}
