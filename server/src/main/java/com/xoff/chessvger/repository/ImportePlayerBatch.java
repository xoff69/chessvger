package com.xoff.chessvger.repository;

import com.xoff.chessvger.chess.player.CommonPlayer;
import com.xoff.chessvger.chess.player.FamousPlayerManager;
import com.xoff.chessvger.chess.player.SynonymPlayerManager;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.PgnUtil;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImportePlayerBatch {

  @Autowired
  private PlayerRepository playerRepository;

  private String location=ParamConstants.PLAYERS_PATH;
  private static final int IDNUMBER = 15;
  private static final int NAME = 61;
  private static final int FED = 4;
  private static final int SEX = 4;
  private static final int TIT = 5;
  private static final int WTIT = 5;
  private static final int OTIT = 15;
  private static final int FOA = 4;
  private static final int SRTNG = 6;
  private static final int SGM = 4;
  private static final int SK = 3;
  private static final int RRTNG = 6;
  private static final int RGM = 4;
  private static final int RK = 3;
  private static final int BRTNG = 6;
  private static final int BGM = 4;
  private static final int BK = 3;
  private static final int BDAY = 6;
  private static final int FLAG = 4;

  public long importeFidePlayer() {

    try (Stream<String> stream = Files.lines(Paths.get(location))) {
      final int[] compteurLigne = {0};
      stream.forEach((line) -> {

        CommonPlayerEntity player = new CommonPlayerEntity();

        int debut = 0;
        int fin = IDNUMBER;
        try {
          player.setId(Long.parseLong(line.substring(debut, fin).trim()));
        } catch (Exception e) {
          log.error(" importeFidePlayer " + line);
        }
        debut = fin;
        fin = fin + NAME;
        player.setName(line.substring(debut, fin).trim());
        player.setNakedName(PgnUtil.playerDenudeName(player.getName()));
        debut = fin;
        fin = fin + FED;

        player.setFex(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + SEX;

        player.setSex(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + TIT;

        player.setTit(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + WTIT;

        player.setWtit(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + OTIT;

        player.setOtit(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + FOA;

        player.setFoa(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + SRTNG;

        player.setSsrtng(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + SGM;

        player.setSgm(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + SK;
        player.setSk(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + RRTNG;
        player.setRrtng(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + RGM;
        player.setRgm(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + RK;
        player.setRk(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + BRTNG;
        player.setBrtng(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + BGM;
        player.setBgm(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + BK;
        player.setBk(line.substring(debut, fin).trim());
        debut = fin;
        fin = fin + BDAY;
        int max = Math.min(fin, line.length());
        player.setBday(line.substring(debut, max - 1).trim());
        debut = fin;
        fin = fin + FLAG;
        max = Math.min(fin, line.length());
        if (max > debut) {
          player.setFlag(line.substring(debut, max - 1).trim());
        }
       log.info(player.toString());
        if (compteurLigne[0] > 0) {

           // add(player.getIdnumber(), player);
          playerRepository.save(player);
          //famous.addIfRelevant(player);
        }
        compteurLigne[0]++;
      });

    } catch (IOException ex) {
      log.error(ex.getMessage());
    }
    return playerRepository.count();
  }

}
