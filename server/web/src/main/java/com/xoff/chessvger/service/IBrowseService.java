package com.xoff.chessvger.service;

import com.xoff.chessvger.view.StatBrowserView;

import java.util.List;

public interface IBrowseService {

    List<StatBrowserView> loadAll(long bdId);
}
