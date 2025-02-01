package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.DataSourceContextHolder;
import com.xoff.chessvger.repository.DynamicDataSourceService;
import com.xoff.chessvger.repository.UserEntity;
import com.xoff.chessvger.repository.UserRepository;
import com.xoff.chessvger.ui.UserDTO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
  @Autowired
  private DynamicDataSourceService dynamicDataSourceService;
  @Autowired
  private UserRepository userRespository;

  public UserDTO getUserByUsername(String username) {
    dynamicDataSourceService.addNewDataSource("common",
        "jdbc:postgresql://db_chessvger/chessvger",
        "chessvger",
        "chessvger","common");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("common");
    return mapToDTO(userRespository.findByLogin(username));
  }

  public Long count() {
    dynamicDataSourceService.addNewDataSource("common",
        "jdbc:postgresql://db_chessvger/chessvger",
        "chessvger",
        "chessvger","common");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("common");
    return userRespository.count();
  }
  public List<UserDTO> findAll(){
    dynamicDataSourceService.addNewDataSource("common",
        "jdbc:postgresql://db_chessvger/chessvger",
        "chessvger",
        "chessvger","common");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("common");
    org.springframework.data.domain.Page<UserEntity> page=userRespository.findAll(
        org.springframework.data.domain.Pageable.ofSize(5));
    return page.stream()
        .map(this::mapToDTO)
        .collect(Collectors.toList());
  }
  public UserEntity findByLoginAndPassword(String login, String password){
    dynamicDataSourceService.addNewDataSource("common",
        "jdbc:postgresql://db_chessvger/chessvger",
        "chessvger",
        "chessvger","common");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("common");
    return userRespository.findByLoginAndPassword(login, password);
  }

  private UserDTO mapToDTO(UserEntity userEntity) {
    UserDTO dto = new UserDTO();
    dto.setId(userEntity.getId());
    dto.setLogin(userEntity.getLogin());
    dto.setDescription(userEntity.getDescription());
    dto.setPassword(userEntity.getPassword());
    dto.setDateCreated(userEntity.getDateCreated());
    dto.setDateUpdated(userEntity.getDateUpdated());
    dto.setProfil(userEntity.getProfil());
    dto.setTenantId(userEntity.getTenant().getId());
    return dto;
  }
}
