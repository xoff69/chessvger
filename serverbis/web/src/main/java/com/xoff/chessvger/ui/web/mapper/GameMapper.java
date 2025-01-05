package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.chess.player.CommonPlayer;
import com.xoff.chessvger.common.GlobalManager;
import com.xoff.chessvger.util.PgnUtil;
import com.xoff.chessvger.view.GameView;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.context.i18n.LocaleContextHolder;

@Mapper(componentModel = "spring")
public interface GameMapper {

  GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

  @Named("mapPlayerId2Name")
  static String mapPlayerId2Name(long id) {
    CommonPlayer w = GlobalManager.getInstance().getCommonPlayerManager().findById(id);
    return w == null ? "jid=" + id : w.getName();
  }

  @Named("formatDate")
  static String formatDate(long dt) {
    Locale locale = LocaleContextHolder.getLocale();
    DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, locale);
    return dateFormat.format(new Date(dt));
    }



  @Mapping(source = "whiteFideId", target = "joueurBlanc", qualifiedByName = "mapPlayerId2Name")
  @Mapping(source = "blackFideId", target = "joueurNoir", qualifiedByName = "mapPlayerId2Name")
  @Mapping(source = "result", target = "resultat")
  @Mapping(source = "lastUpdate", target = "lastUpdate", qualifiedByName = "formatDate")
  @Mapping(source = "metaCommonGame.metaCommentaireMove", target = "metaCommentaireMove")
  @Mapping(source = "metaCommonGame.commentairePartie", target = "commentairePartie")
  @Mapping(source = "metaCommonGame.lastSeen", target = "lastSeen")
  @Mapping(source = "metaCommonGame.flipBoard", target = "flipBoard")
  @Mapping(source = "metaCommonGame.source", target = "source")
  @Mapping(source = "metaCommonGame.commentateurPrincipal", target = "commentateurPrincipal")
  GameView commonGame2GameView(CommonGame commonGame);


  List<CommonGame> map(List<GameView> gameViews);

  CommonGame gameView2CommonGame(GameView gameView);

  List<GameView> mapListEntity2Dto(List<CommonGame> commonGames);


}
