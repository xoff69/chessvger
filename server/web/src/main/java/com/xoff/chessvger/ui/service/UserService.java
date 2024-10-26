package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.pack.Pack;
import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.ui.Pageable;
import com.xoff.chessvger.ui.web.view.PageView;
import com.xoff.chessvger.ui.web.view.UserDto;
import java.util.List;

public interface UserService {

  User create(UserDto dto);

  UserDto update(Long id, UserDto dto);

  UserDto findByLoginAndPassword(String name, String password);

  PageView<UserDto> findAll(Pageable paging);

  List<UserDto> saveAll(List<UserDto> list);

  UserDto findById(Long id);

  boolean delete(Long id);

  public List<User> list();
}
