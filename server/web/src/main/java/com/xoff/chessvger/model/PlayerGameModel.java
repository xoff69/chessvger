package com.xoff.chessvger.model;

import com.xoff.chessvger.chess.player.CommonPlayer;

import lombok.Data;
import java.sql.Date;

@Data
public class PlayerGameModel {
    CommonPlayer player;
    int count;
}
