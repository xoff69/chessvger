package com.xoff.chessvger.admin.ui.service;

import com.xoff.chessvger.admin.repository.UserEntity;
import com.xoff.chessvger.admin.ui.UserDTO;
import java.util.List;

public interface UserService {
  public Long count();

  public  List<UserDTO> findAll();
  public UserDTO getUserByUsername(String username);
  UserEntity findByLoginAndPassword(String login, String password);
}
