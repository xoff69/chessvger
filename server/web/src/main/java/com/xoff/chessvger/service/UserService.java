package com.xoff.chessvger.service;

import com.xoff.chessvger.model.TenantEntity;
import com.xoff.chessvger.ui.web.controller.tools.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public Long count();
    // TODO renommer findByID /?
    public UserDTO getById(long id);

    public List<UserDTO> findAll(Pageable pageable);

    public UserDTO getUserByUsername(String username);
    public Optional<TenantEntity> getTenant(long tenantId);
    public Optional<TenantEntity> getByUserId(long userId);
    public UserDTO findByLoginAndPassword(String login, String password);
}
