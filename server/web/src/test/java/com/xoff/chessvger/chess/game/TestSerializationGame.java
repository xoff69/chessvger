package com.xoff.chessvger.chess.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.xoff.chessvger.common.GlobalManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
public class TestSerializationGame {
  @Test
  public void testCommonGameJson() {
    try {
      CommonGame cg = new CommonGame();
      cg.setId(253);
      cg.setFirstMove("c4");
      cg.getMetaCommonGame().setCommentairePartie("aaaaaaaaaaaaa");
      String res=  GlobalManager.getInstance().getObjectMapper().writeValueAsString(cg);
      System.out.println(res);
      assertTrue(res.contains("c4"));
  } catch (Exception e) {
    e.printStackTrace();
  }
}
  @Test
  public void testCommonGameSerialization() {
    try {
      CommonGame cg = new CommonGame();
      cg.setId(253);
      cg.setFirstMove("c4");
      cg.getMetaCommonGame().setCommentairePartie("aaaaaaaaaaaaa");
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      ObjectOutputStream out = new ObjectOutputStream(baos);

      out.writeObject(cg);
      byte[] b = baos.toByteArray();
      out.close();

      ByteArrayInputStream bais = new ByteArrayInputStream(b);
      ObjectInputStream in = new ObjectInputStream(bais);
      CommonGame cg2 = (CommonGame) in.readObject();
      in.close();

      assertEquals(cg.getId(), cg2.getId());
      assertEquals(cg.getMetaCommonGame().getCommentairePartie(), cg2.getMetaCommonGame().getCommentairePartie());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
