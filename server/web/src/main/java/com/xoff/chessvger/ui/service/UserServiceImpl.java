package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.ui.web.mapper.UserMapper;
import com.xoff.chessvger.ui.web.navigation.Page;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PageView;
import com.xoff.chessvger.common.UserTenant;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


  @Autowired
  private UserMapper userMapper;

  @Override
  public UserTenant findByLoginAndPassword(String name, String password) {
// FIXME Constantes

    GlobalManager.getInstance().getCallStatManager().appendStat("CONNEXION");
    return userMapper.entity2Dto(
        GlobalManager.getInstance().getUserManager().findByLoginAndPassword(name, password));
  }

  @Override
  public List<UserTenant> saveAll(List<UserTenant> list) {
    return userMapper.mapListEntity2Dto(
        GlobalManager.getInstance().getUserManager().saveAll(userMapper.map(list)));
  }

  @Override
  public UserTenant findById(Long id) {
    User found = GlobalManager.getInstance().getUserManager().get(id);

    if (found == null) {
      return null;
    } else {

      UserTenant userDto = userMapper.entity2Dto(found);
      // TODO populate user pack
      return userDto;
    }
  }

  @Override
  public boolean delete(Long id) {

    UserTenant dto = findById(id);
    if (dto != null) {
      GlobalManager.getInstance().getUserManager().deleteById(id);
      return true;
    } else {
      return false;
    }

  }


  @Override
  public User create(UserTenant dto) {
    User toSave = userMapper.dto2entity(dto);
    return GlobalManager.getInstance().getUserManager().create(toSave);
  }


  @Override
  public UserTenant update(Long id, UserTenant dto) {
    UserTenant toUpdate = findById(id);
    if (toUpdate == null) {
      return null;
    }
    User toSave = userMapper.dto2entity(dto);
    toSave.setId(id);
    GlobalManager.getInstance().getUserManager().update(toSave);
    return userMapper.entity2Dto(toSave);

  }

  public PageView<UserTenant> findAll(Pageable paging) {
    List<User> items = GlobalManager.getInstance().getUserManager().findAll();
    return Page.compute(paging, userMapper.mapListEntity2Dto(items));
  }
  public List<User> list(){
    return  GlobalManager.getInstance().getUserManager().findAll();
  }
}
