package com.xoff.chessvger.chess.user;

import com.xoff.chessvger.common.ICommonManager;
import com.xoff.chessvger.model.User;

public interface IUserManager extends ICommonManager<Long, User> {


  User findByLoginAndPassword(String login, String password);
}
