package com.xoff.chessvger.util;

import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.model.CommonGame;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertFalse;

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
