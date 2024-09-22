package com.xoff.chessvger.chess.callstat;

import com.xoff.chessvger.common.ACommonManager;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.util.Constants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CallStatManager extends ACommonManager<Long, CallStat>
    implements ICallStatManager {
  public void appendStat(String name){
      CallStat callStat = new CallStat();
      callStat.setName(name);
      callStat.setTimestamp(System.currentTimeMillis());
      GlobalManager.getInstance().getCallStatManager().create(callStat);

  }

  public CallStatManager() {
    super(ParamConstants.DATA_FOLDER_COMMON + "CallStatMap" + Constants.MAP_SFX);
  }


}
