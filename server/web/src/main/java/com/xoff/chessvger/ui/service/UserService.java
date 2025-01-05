package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PageView;
import com.xoff.chessvger.common.UserTenant;
import java.util.List;

public interface UserService {

  User create(UserTenant dto);

  UserTenant update(Long id, UserTenant dto);

  UserTenant findByLoginAndPassword(String name, String password);

  PageView<UserTenant> findAll(Pageable paging);

  List<UserTenant> saveAll(List<UserTenant> list);

  UserTenant findById(Long id);

  boolean delete(Long id);

  public List<User> list();
}
