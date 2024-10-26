package com.xoff.chessvger.ui.service;


import com.xoff.chessvger.chess.userpack.UserPack;
import com.xoff.chessvger.exception.CrudException;
import com.xoff.chessvger.ui.web.form.UserPackForm;
import com.xoff.chessvger.ui.web.view.UserPackDto;

public interface UserPackService {
  public UserPack create(UserPackForm form) throws CrudException;
}
