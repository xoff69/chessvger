package com.xoff.chessvger.util;

import static org.junit.jupiter.api.Assertions.assertFalse;

import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.common.ParamConstants;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("IT")
public class TestRejetUtil {

  @Test
  @DisplayName("testRejet")
  public void testRejet() throws IOException {
    CommonGame c = new CommonGame();
    RejetUtil rejetUtil = new RejetUtil();
    rejetUtil.ecritRejet(c, "VAR");
    rejetUtil.finish();
    assertFalse(FileUtils.isEmptyDirectory(new File(ParamConstants.REP_REJET)));
  }
}
