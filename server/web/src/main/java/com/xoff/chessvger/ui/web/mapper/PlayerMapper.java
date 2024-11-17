package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.player.CommonPlayer;
import com.xoff.chessvger.view.JoueurView;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlayerMapper {


  JoueurView commonPlayer2JoueurView(CommonPlayer commonPlayer);

  List<CommonPlayer> map(List<JoueurView> views);

  CommonPlayer joueurView2CommonGame(JoueurView view);

  List<JoueurView> mapListEntity2Dto(List<CommonPlayer> games);


}
