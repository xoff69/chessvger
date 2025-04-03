package com.xoff.chessvger.chess.user;

import com.xoff.chessvger.common.ACommonManager;
import com.xoff.chessvger.common.ParamConstants;
import com.xoff.chessvger.model.User;
import com.xoff.chessvger.util.Constants;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UserManager extends ACommonManager<Long, User> implements IUserManager {


    public UserManager() {

        super(ParamConstants.DATA_FOLDER_COMMON + "UserMap" + Constants.MAP_SFX);

    }

    public User findByLoginAndPassword(String login, String password) {
        List<User> users = findAll();
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

}
