package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.DataSourceContextHolder;
import com.xoff.chessvger.repository.DynamicDataSourceService;
import org.springframework.stereotype.Service;

@Service

public class GameService {
  private final DynamicDataSourceService dynamicDataSourceService;

  public GameService(DynamicDataSourceService dynamicDataSourceService) {
    this.dynamicDataSourceService = dynamicDataSourceService;
  }

  public void handleGameAction() {
    // Logique pour ajouter une nouvelle source de données
    dynamicDataSourceService.addNewDataSource("newDb",
        "jdbc:mysql://localhost:3306/newdb",
        "newUser",
        "newPassword");

    // Changer la source de données actuelle pour "newDb"
    DataSourceContextHolder.setDataSource("newDb");

    // Vous pouvez maintenant exécuter des requêtes sur cette nouvelle base de données
    // Par exemple, vous pouvez appeler un repository ou un EntityManager pour interagir avec la base de données "newDb".
  }

}
