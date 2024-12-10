package com.xoff.chessvger.queues.gameofplayer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameOfAPlayer {
  private long id;
  private long gameId;
  private long playerId;
}
