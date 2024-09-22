package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.ui.web.view.StatBrowserView;
import java.util.List;

public interface IBrowseService {

  public List<StatBrowserView> loadAll(long bdId);
}
