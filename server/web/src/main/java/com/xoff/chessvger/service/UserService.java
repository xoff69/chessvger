package com.xoff.chessvger.service;

import com.xoff.chessvger.ui.web.controller.tools.UserDTO;

import java.util.List;

public interface UserService {
    public Long count();
    // TODO renommer findByID /?
    public UserDTO getById(long id);

    public List<UserDTO> findAll();

    public UserDTO getUserByUsername(String username);

    public UserDTO findByLoginAndPassword(String login, String password);
}
