package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.filter.Filter;
import com.xoff.chessvger.ui.web.form.FilterForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class FilterMapper {

  @Named("mapString2Int")
  static int mapString2Int(String value) {
    try {
      return Integer.parseInt(value);
    } catch (Exception e) {
      return 0;
    }
  }

  @Mapping(target = "ecoSelection", ignore = true)
  @Mapping(target = "coupsSelection", ignore = true)
  @Mapping(target = "dateSelection", ignore = true)
  @Mapping(target = "bdId", ignore = true)
  @Mapping(target = "resultat10", ignore = true)
  @Mapping(target = "resultat12", ignore = true)
  @Mapping(target = "resultat01", ignore = true)
  @Mapping(target = "positionjcTraitNoir", ignore = true)
  @Mapping(target = "positionjcTraitBlanc", ignore = true)
  @Mapping(target = "positionjcPetitRoqueBlanc", ignore = true)
  @Mapping(target = "positionjcPetitRoqueNoir", ignore = true)
  @Mapping(target = "positionjcGrandRoqueBlanc", ignore = true)
  @Mapping(target = "positionjcGrandRoqueNoir", ignore = true)
  @Mapping(target = "positionjcTraitInconnu", ignore = true)
  @Mapping(target = "positionjtfFEN", ignore = true)
  public abstract FilterForm entity2Dto(Filter filter);

  @Mapping(source = "nbJourSinceUpdate", target = "nbJourSinceUpdate", qualifiedByName = "mapString2Int")
  @Mapping(source = "selectedModeDate", target = "selectedModeDate", qualifiedByName = "mapString2Int")
  @Mapping(source = "selectedModeCoups", target = "selectedModeCoups", qualifiedByName = "mapString2Int")
  @Mapping(source = "selectedModeEco", target = "selectedModeEco", qualifiedByName = "mapString2Int")
  @Mapping(source = "whiteElo", target = "whiteElo", qualifiedByName = "mapString2Int")
  @Mapping(source = "blackElo", target = "blackElo", qualifiedByName = "mapString2Int")
  @Mapping(source = "whiteFideId", target = "whiteFideId", qualifiedByName = "mapString2Int")
  @Mapping(source = "blackFideId", target = "blackFideId", qualifiedByName = "mapString2Int")
  @Mapping(source = "nbcoup1", target = "nbcoup1", qualifiedByName = "mapString2Int")
  @Mapping(source = "nbcoup2", target = "nbcoup2", qualifiedByName = "mapString2Int")
  @Mapping(target = "nom", ignore = true)
  public abstract Filter form2entity(FilterForm form);

}
