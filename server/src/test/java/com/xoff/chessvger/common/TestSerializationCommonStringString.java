package com.xoff.chessvger.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.xoff.chessvger.EnvManager;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
public class TestSerializationCommonStringString {


  @Test
  @DisplayName("testCommonStringString")
  public void testCommonStringString() {
    String where = EnvManager.getInstance().getValue(EnvManager.RUN_FOLDER_PARAM) + "/temp/strStr";
    try {
      AdbCommonKeyString adbc = new AdbCommonKeyString<String>(where);

      String v1 = "aaaaaa";

      adbc.add("1L", v1);
      String v2 = "jjjjjjjjjjjj";
      adbc.add("2L", v2);
      String v3 = "jjjccccjjjjjjjjj";
      adbc.add("3L", v3);

      List<String> l = adbc.list();
      assertEquals(l.size(), 3);
      for (String p : l) {
        System.out.println(p);
      }

      List<String> k = adbc.keyset();

      assertEquals(k.size(), 3);
      for (String p : k) {
        System.out.println(p);
      }


    } catch (Exception e) {
      e.printStackTrace();
    }

  }


}
