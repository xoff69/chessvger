/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xoff.chessvger.chess.favorite;

import com.xoff.chessvger.common.AdbCommonKeyLong;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FavoriteMap extends AdbCommonKeyLong<Favorite> {


  public FavoriteMap(String dbName) {
    super(ParamConstants.DATA_FOLDER_COMMON + "FavoriteMap" + Constants.MAP_SFX);

  }

}
