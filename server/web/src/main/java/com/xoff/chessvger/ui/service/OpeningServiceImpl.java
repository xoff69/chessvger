package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.opening.Opening;
import com.xoff.chessvger.common.GlobalManager;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OpeningServiceImpl implements IOpeningService {
  @Override
  public List<Opening> getAll() {


    return GlobalManager.getInstance().getOpeningManager().list();
  }
}
