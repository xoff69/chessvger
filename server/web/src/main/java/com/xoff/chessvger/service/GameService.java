package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.CommonGameEntity;
import com.xoff.chessvger.repository.DataSourceContextHolder;
import com.xoff.chessvger.repository.DynamicDataSourceService;
import com.xoff.chessvger.repository.GameRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
  @Autowired
  private  DynamicDataSourceService dynamicDataSourceService;

  @Autowired
  private GameRepository gameRepository;
  public long count() {

    // jdbc:postgresql://db_chessvger/chessvger_admin_database?currentSchema=main
    dynamicDataSourceService.addNewDataSource("newDb",
        "jdbc:postgresql://db_chessvger/chessvger_admin_database",
        "chessvger",
        "chessvger","main");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("newDb");
    return gameRepository.count();
  }
  public List<CommonGameEntity> handleAllGames() {
    // Logique pour ajouter une nouvelle source de données
    System.out.println("test repo dynamique");
    // jdbc:postgresql://db_chessvger/chessvger_admin_database?currentSchema=main
    dynamicDataSourceService.addNewDataSource("newDb",
        "jdbc:postgresql://db_chessvger/chessvger_admin_database",
        "chessvger",
        "chessvger","main");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("newDb");
    org.springframework.data.domain.Page<CommonGameEntity> page=gameRepository.findAll(
        org.springframework.data.domain.Pageable.ofSize(5));

    return  page.stream().toList();
    // Vous pouvez maintenant exécuter des requêtes sur cette nouvelle base de données
    // Par exemple, vous pouvez appeler un repository ou un EntityManager pour interagir avec la base de données "newDb".
  }
  public CommonGameEntity findById(long id) {
    // Logique pour ajouter une nouvelle source de données
    System.out.println("test repo findById id=" + id);
    // jdbc:postgresql://db_chessvger/chessvger_admin_database?currentSchema=main
    dynamicDataSourceService.addNewDataSource("newDb",
        "jdbc:postgresql://db_chessvger/chessvger_admin_database",
        "chessvger",
        "chessvger","main");



    return  gameRepository.findById(id);
  }
}
