package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.repertoire.Repertoire;
import com.xoff.chessvger.view.RepertoireDto;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RepertoireMapper {
  RepertoireMapper INSTANCE = Mappers.getMapper(RepertoireMapper.class);


  public abstract List<RepertoireDto> mapListEntity2Dto(List<Repertoire> repertoires);
}