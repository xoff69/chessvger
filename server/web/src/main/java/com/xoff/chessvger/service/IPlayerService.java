package com.xoff.chessvger.service;
import com.xoff.chessvger.chess.player.CommonPlayer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.xoff.chessvger.chess.player.CommonPlayer;
import com.xoff.chessvger.chess.player.CommonPlayer;
public interface IPlayerService {
     Page<CommonPlayer> findAll(Pageable pageable);
   Long count();
  CommonPlayer findById(Long id);
}
