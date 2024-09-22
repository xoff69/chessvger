package com.xoff.chessvger.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.EnvManager;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
@Slf4j
public class TestSerializationCommonLong {
  String where = EnvManager.getInstance().getValue(EnvManager.RUN_FOLDER_PARAM) + "/temp/strLong";


  @Test
  @DisplayName("TestSerializationCommonLong")
  public void testCommonStringLong() {

    try {
      AdbCommonKeyLong adbc = new AdbCommonKeyLong<String>(where);

      String v1 = "aaaaaa";

      adbc.add(1L, v1);
      String v2 = "jjjjjjjjjjjj";
      adbc.add(2L, v2);
      String v3 = "jjjccccjjjjjjjjj";
      adbc.add(3L, v3);
      List<String> l = adbc.list();
      assertEquals(l.size(), 3);


      List<Long> k = adbc.keyset();

      assertEquals(k.size(), 3);


    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }

  }
}
