package com.xoff.chessvger.repository;

import com.xoff.chessvger.chess.player.CommonPlayer;
import com.xoff.chessvger.ui.web.mapper.GameMapper;
import com.xoff.chessvger.ui.web.view.JoueurView;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
  PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

  CommonPlayerEntity player2Entity(Player player);

  List<CommonPlayerEntity> map(List<Player> players);



}
