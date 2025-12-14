package com.xoff.chessvger.service;

import com.xoff.chessvger.model.TenantEntity;
import com.xoff.chessvger.ui.web.controller.tools.UserDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Long count();

    // TODO renommer findByID /?
    UserDTO getById(long id);

    List<UserDTO> findAll(Pageable pageable);

    UserDTO getUserByUsername(String username) throws ServiceException;

    Optional<TenantEntity> getTenant(long tenantId);

    Optional<TenantEntity> getByUserId(long userId);

    UserDTO findByLoginAndPassword(String login, String password);
}
