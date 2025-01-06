package com.xoff.chessvger.ui.service;

import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.chess.userpack.UserPack;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.exception.CrudException;
import com.xoff.chessvger.ui.web.form.UserPackForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserPackServiceImpl implements UserPackService {



  public UserPack create(UserPackForm form)  throws CrudException {
    // verifier que n existe pas deja
    // verifier que les 2 id existent deja
    UserPack toSave =new UserPack();
    toSave.setPackId(form.getPackId());
    toSave.setUserId(form.getUserId());
    toSave.setId(form.getId());
    return GlobalManager.getInstance().getUserPackManager().create(toSave);

  }
}
