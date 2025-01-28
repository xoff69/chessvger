package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.repository.UserEntity;
import com.xoff.chessvger.repository.UserRepository;
import com.xoff.chessvger.ui.UserDTO;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PageView;
import com.xoff.chessvger.common.UserTenant;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public interface UserService {
  public Long count();

  public  List<UserDTO> findAll();
  public UserDTO getUserByUsername(String username);
  UserEntity findByLoginAndPassword(String login, String password);
}
