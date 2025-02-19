package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.player.CommonPlayer;
import com.xoff.chessvger.ui.web.view.JoueurView;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-14T19:14:26-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class PlayerMapperImpl implements PlayerMapper {

    @Override
    public JoueurView commonPlayer2JoueurView(CommonPlayer commonPlayer) {
        if ( commonPlayer == null ) {
            return null;
        }

        JoueurView joueurView = new JoueurView();

        return joueurView;
    }

    @Override
    public List<CommonPlayer> map(List<JoueurView> views) {
        if ( views == null ) {
            return null;
        }

        List<CommonPlayer> list = new ArrayList<CommonPlayer>( views.size() );
        for ( JoueurView joueurView : views ) {
            list.add( joueurView2CommonGame( joueurView ) );
        }

        return list;
    }

    @Override
    public CommonPlayer joueurView2CommonGame(JoueurView view) {
        if ( view == null ) {
            return null;
        }

        CommonPlayer commonPlayer = new CommonPlayer();

        return commonPlayer;
    }

    @Override
    public List<JoueurView> mapListEntity2Dto(List<CommonPlayer> games) {
        if ( games == null ) {
            return null;
        }

        List<JoueurView> list = new ArrayList<JoueurView>( games.size() );
        for ( CommonPlayer commonPlayer : games ) {
            list.add( commonPlayer2JoueurView( commonPlayer ) );
        }

        return list;
    }
}
