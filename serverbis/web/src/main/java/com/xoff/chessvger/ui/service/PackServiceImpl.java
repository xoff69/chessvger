package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.pack.Pack;
import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.chess.userpack.UserPack;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.ui.web.mapper.PackMapper;
import com.xoff.chessvger.ui.web.navigation.Page;
import com.xoff.chessvger.view.PackDto;
import com.xoff.chessvger.view.PageView;
import com.xoff.chessvger.view.UserPackDto;
import com.xoff.chessvger.view.UserPackDto;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PackServiceImpl implements PackService {
  @Autowired
  PackMapper packMapper;

  public void create(Pack pack) {
    GlobalManager.getInstance().getPackManager().create(pack);
  }
  public List<Pack> list(){
    List<Pack> packs = GlobalManager.getInstance().getPackManager().findAll();
    return packs;
  }
  public PageView<PackDto> managePage(Pageable paging, long userId) {
    // FIXME , se servir du user id

    List<Pack> packs = GlobalManager.getInstance().getPackManager().findAll();
    PageView<PackDto> pageView = Page.compute(paging, packMapper.mapListEntity2Dto(packs));

    return pageView;
  }

  public PageView<PackDto> managePage(Pageable paging) {

    List<Pack> packs = GlobalManager.getInstance().getPackManager().findAll();
    PageView<PackDto> pageView = Page.compute(paging, packMapper.mapListEntity2Dto(packs));

    return pageView;
  }

  public PageView<UserPackDto> managePageUserPack(Pageable paging) {
    List<UserPack> userPacks = GlobalManager.getInstance().getUserPackManager().findAll();
    List<UserPackDto> userPacksPackDtos = new ArrayList<>();
log.info("user pack ");
    for (UserPack userPack : userPacks) {
      UserPackDto userPackDto = new UserPackDto();
      userPackDto.setId(userPack.getId());
      User user=GlobalManager.getInstance().getUserManager().get(userPack.getUserId());
      userPackDto.setUserName(user==null?"UNKNOWN":user.getName());
      Pack pack=GlobalManager.getInstance().getPackManager().get(userPack.getPackId());
      userPackDto.setPackName(pack==null?"UNKNOWN":pack.getName());
      userPacksPackDtos.add(userPackDto);
    }

    PageView<UserPackDto> pageView = Page.compute(paging, userPacksPackDtos);

    return pageView;
  }
}
