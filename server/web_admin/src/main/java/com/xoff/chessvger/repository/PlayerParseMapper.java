package com.xoff.chessvger.repository;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PlayerParseMapper {
  PlayerParseMapper INSTANCE = Mappers.getMapper(PlayerParseMapper.class);

  CommonPlayerEntity player2Entity(Player player);

  List<CommonPlayerEntity> map(List<Player> players);



}
