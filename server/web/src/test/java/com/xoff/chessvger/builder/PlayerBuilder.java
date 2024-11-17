package com.xoff.chessvger.builder;

import com.xoff.chessvger.chess.player.CommonPlayer;
import java.util.Random;

public class PlayerBuilder {
  private static final Random rand = new Random();

  public static final int buildElo() {
    return 10000 + rand.nextInt(100000) % 1500;
  }

  public static CommonPlayer buildPlayer() {

    CommonPlayer player = new CommonPlayer();
    player.setIdnumber(rand.nextInt(10000));
    player.setName("fakeName_" + rand.nextInt());
    player.setNakedName("fakeName_");

    player.setFex("F");
    player.setSex("M");
    player.setTit("GM");
    player.setWtit("GM");
    player.setOtit("GM");
    player.setFoa("FOA");
    player.setSsrtng(String.valueOf(buildElo()));
    player.setSgm("A");
    player.setSk("SK");
    player.setRrtng(String.valueOf(buildElo()));
    player.setRgm("RGM");
    player.setRk("RK");
    player.setRrtng(String.valueOf(buildElo()));
    player.setBgm("BGM");
    player.setBk("BK");
    player.setBday("12/12/2012");
    player.setFlag("A");

    return player;
  }
}
