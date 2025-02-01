package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.UserEntity;
import com.xoff.chessvger.ui.UserDTO;
import java.util.List;

public interface UserService {
  public Long count();

  public  List<UserDTO> findAll();
  public UserDTO getUserByUsername(String username);
  UserEntity findByLoginAndPassword(String login, String password);
}
