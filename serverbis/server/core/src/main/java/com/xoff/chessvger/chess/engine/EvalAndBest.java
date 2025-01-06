package com.xoff.chessvger.chess.engine;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
public class EvalAndBest {

  private float eval = 0.0f;
  private String bestmove = StringUtils.EMPTY;


}
