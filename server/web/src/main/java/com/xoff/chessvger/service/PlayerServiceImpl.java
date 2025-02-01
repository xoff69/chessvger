package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.CommonPlayerEntity;
import com.xoff.chessvger.repository.PlayerRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PlayerServiceImpl implements IPlayerService {

  @Autowired
  private PlayerRepository playerRepository;

  public Long count() {
    return playerRepository.count();
  }

  public List<CommonPlayerEntity> findAll() {
    org.springframework.data.domain.Page<CommonPlayerEntity> page =
        playerRepository.findAll(org.springframework.data.domain.Pageable.ofSize(5));
    return page.stream().toList();
  }

}
