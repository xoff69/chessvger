package com.xoff.chessvger.ui.service;


import com.xoff.chessvger.chess.pack.Pack;
import com.xoff.chessvger.chess.userpack.UserPack;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PackDto;
import com.xoff.chessvger.view.PageView;
import com.xoff.chessvger.view.UserPackDto;
import java.util.List;

public interface PackService {
  public void create(Pack pack);
  public List<Pack> list();
  public PageView<PackDto> managePage(Pageable paging) ;
  public PageView<PackDto> managePage(Pageable paging,long userId) ;
  public PageView<UserPackDto> managePageUserPack(Pageable paging) ;
}
