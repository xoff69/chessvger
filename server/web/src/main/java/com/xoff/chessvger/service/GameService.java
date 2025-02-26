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

    return gameRepository.count();
  }
  public List<CommonGameEntity> handleAllGames() {
    org.springframework.data.domain.Page<CommonGameEntity> page=gameRepository.findAll(
        org.springframework.data.domain.Pageable.ofSize(5));

    return  page.stream().toList();
    // Vous pouvez maintenant exécuter des requêtes sur cette nouvelle base de données
    // Par exemple, vous pouvez appeler un repository ou un EntityManager pour interagir avec la base de données "newDb".
  }
  public CommonGameEntity findById(long id) {

    return  gameRepository.findById(id);
  }
}
