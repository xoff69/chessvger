package com.xoff.chessvger.chess.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.EnvManager;
import com.xoff.chessvger.common.AdbCommonKeyLong;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
@Slf4j
public class TestSerializationPlayerOpening {


  @Test
  public void testCommonKeyLongCommonPlayerAndOpening() {
    String where = EnvManager.getInstance().getValue(EnvManager.RUN_FOLDER_PARAM) + "/temp/player";

    try {
      AdbCommonKeyLong adbc = new AdbCommonKeyLong<CommonPlayer>(where);

      CommonPlayer player1 = new CommonPlayer();
      player1.setIdnumber(1L);
      player1.setName("toto");
      adbc.add(1L, player1);
      CommonPlayer player2 = new CommonPlayer();
      player2.setIdnumber(2L);
      player2.setName("xxx");
      adbc.add(2L, player2);
      CommonPlayer player3 = new CommonPlayer();
      player3.setIdnumber(3L);
      player3.setName("ddd");
      adbc.add(3L, player3);


      List<CommonPlayer> l = adbc.list();
      assertEquals(l.size(), 3);
      for (CommonPlayer p : l) {
        log.info(p.toString());
      }

      adbc.commit();


    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
  }

}
