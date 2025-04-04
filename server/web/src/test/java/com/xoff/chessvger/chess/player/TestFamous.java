package com.xoff.chessvger.chess.player;

import com.xoff.chessvger.model.CommonPlayer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("IT")
public class TestFamous {


    @Test
    @DisplayName("testFamous")
    public void testFamous() {
        System.out.println("testFamous");
        FamousPlayerManager fpm = new FamousPlayerManager();
        try {
            String nom = "xxx";
            CommonPlayer player2 = new CommonPlayer();
            player2.setId(2L);
            player2.setTit("GM");
            player2.setSsrtng("3000");
            player2.setName(nom);
            assertTrue(fpm.addIfRelevant(player2));
            // test pas ajout
            CommonPlayer player3 = new CommonPlayer();
            player3.setId(3L);
            player3.setTit("GM");
            player3.setSsrtng("2300");
            player3.setName(nom + nom);

            assertFalse(fpm.addIfRelevant(player3));
            player3.setTit("SM");
            assertFalse(fpm.addIfRelevant(player3));
            player3.setSsrtng("2600");
            player3.setTit("GM");
            assertTrue(fpm.addIfRelevant(player3));
            fpm.keyset();
            assertTrue(fpm.isWellKnown(nom));


        } catch (Exception e) {
            // e.printStackTrace();
        }
        fpm.finish();
    }
}
