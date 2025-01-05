package com.xoff.chessvger.chess.callstat;

import com.xoff.chessvger.common.ICommonManager;

public interface ICallStatManager extends ICommonManager<Long, CallStat> {

  void appendStat(String name);
}
