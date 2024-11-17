package com.xoff.chessvger.ui.service;

import com.google.gson.Gson;
import com.xoff.chessvger.chess.callstat.CallStat;
import com.xoff.chessvger.chess.feature.Feature;
import com.xoff.chessvger.common.GlobalManager;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AdminStatServiceImpl implements IAdminStatService {
  @Override
  public String getAllStat(){

    List<CallStat> stats= GlobalManager.getInstance().getCallStatManager().findAll();
    Gson gson = new Gson();
    String result=gson.toJson(stats);
    log.info("getAllStat result"+result);
    return result;
  }
  @Override
  public void clear(){
    log.info("all stat  delete ");
    GlobalManager.getInstance().getCallStatManager().clear();
  }
}
