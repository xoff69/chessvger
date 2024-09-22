package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.pack.Pack;
import com.xoff.chessvger.chess.repertoire.Repertoire;
import com.xoff.chessvger.ui.web.view.PackDto;
import com.xoff.chessvger.ui.web.view.RepertoireDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PackMapper {
  PackMapper INSTANCE = Mappers.getMapper(PackMapper.class);


  public abstract List<PackDto> mapListEntity2Dto(List<Pack> packs);
}