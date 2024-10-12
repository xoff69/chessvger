package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.chess.game.MetaCommonGame;
import com.xoff.chessvger.ui.web.view.GameView;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-10-11T20:14:39-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class GameMapperImpl implements GameMapper {

    @Override
    public GameView commonGame2GameView(CommonGame commonGame) {
        if ( commonGame == null ) {
            return null;
        }

        GameView gameView = new GameView();

        gameView.setJoueurBlanc( GameMapper.mapPlayerId2Name( commonGame.getWhiteFideId() ) );
        gameView.setJoueurNoir( GameMapper.mapPlayerId2Name( commonGame.getBlackFideId() ) );
        gameView.setResultat( commonGame.getResult() );
        gameView.setLastUpdate( GameMapper.formatDate( commonGame.getLastUpdate() ) );
        gameView.setMetaCommentaireMove( commonGameMetaCommonGameMetaCommentaireMove( commonGame ) );
        gameView.setCommentairePartie( commonGameMetaCommonGameCommentairePartie( commonGame ) );
        gameView.setLastSeen( commonGameMetaCommonGameLastSeen( commonGame ) );
        gameView.setFlipBoard( commonGameMetaCommonGameFlipBoard( commonGame ) );
        gameView.setSource( commonGameMetaCommonGameSource( commonGame ) );
        gameView.setCommentateurPrincipal( commonGameMetaCommonGameCommentateurPrincipal( commonGame ) );
        gameView.setId( (int) commonGame.getId() );
        gameView.setEvent( commonGame.getEvent() );
        gameView.setSite( commonGame.getSite() );
        gameView.setDate( commonGame.getDate() );
        gameView.setRound( commonGame.getRound() );
        gameView.setWhiteTitle( commonGame.getWhiteTitle() );
        gameView.setBlackTitle( commonGame.getBlackTitle() );
        gameView.setWhiteElo( commonGame.getWhiteElo() );
        gameView.setBlackElo( commonGame.getBlackElo() );
        gameView.setEco( commonGame.getEco() );
        gameView.setOpening( commonGame.getOpening() );
        gameView.setEventDate( commonGame.getEventDate() );
        gameView.setNbcoups( commonGame.getNbcoups() );
        gameView.setLastPosition( (int) commonGame.getLastPosition() );
        gameView.setInformationsFaitDeJeu( commonGame.getInformationsFaitDeJeu() );
        gameView.setDeleted( commonGame.isDeleted() );
        gameView.setFirstMove( commonGame.getFirstMove() );
        gameView.setMoves( commonGame.getMoves() );
        gameView.setInteret( commonGame.getInteret() );
        gameView.setTheorique( commonGame.isTheorique() );
        gameView.setFavori( commonGame.isFavori() );
        gameView.setPartieAnalysee( commonGame.isPartieAnalysee() );

        return gameView;
    }

    @Override
    public List<CommonGame> map(List<GameView> gameViews) {
        if ( gameViews == null ) {
            return null;
        }

        List<CommonGame> list = new ArrayList<CommonGame>( gameViews.size() );
        for ( GameView gameView : gameViews ) {
            list.add( gameView2CommonGame( gameView ) );
        }

        return list;
    }

    @Override
    public CommonGame gameView2CommonGame(GameView gameView) {
        if ( gameView == null ) {
            return null;
        }

        CommonGame commonGame = new CommonGame();

        commonGame.setId( gameView.getId() );
        commonGame.setEvent( gameView.getEvent() );
        commonGame.setSite( gameView.getSite() );
        commonGame.setPartieAnalysee( gameView.isPartieAnalysee() );
        commonGame.setDate( gameView.getDate() );
        commonGame.setEventDate( gameView.getEventDate() );
        commonGame.setRound( gameView.getRound() );
        commonGame.setWhiteTitle( gameView.getWhiteTitle() );
        commonGame.setBlackTitle( gameView.getBlackTitle() );
        commonGame.setWhiteElo( gameView.getWhiteElo() );
        commonGame.setBlackElo( gameView.getBlackElo() );
        commonGame.setEco( gameView.getEco() );
        commonGame.setOpening( gameView.getOpening() );
        commonGame.setNbcoups( gameView.getNbcoups() );
        commonGame.setLastPosition( gameView.getLastPosition() );
        commonGame.setInformationsFaitDeJeu( gameView.getInformationsFaitDeJeu() );
        if ( gameView.getLastUpdate() != null ) {
            commonGame.setLastUpdate( Long.parseLong( gameView.getLastUpdate() ) );
        }
        commonGame.setDeleted( gameView.isDeleted() );
        commonGame.setFirstMove( gameView.getFirstMove() );
        commonGame.setMoves( gameView.getMoves() );
        commonGame.setInteret( gameView.getInteret() );
        commonGame.setTheorique( gameView.isTheorique() );
        commonGame.setFavori( gameView.isFavori() );

        return commonGame;
    }

    @Override
    public List<GameView> mapListEntity2Dto(List<CommonGame> commonGames) {
        if ( commonGames == null ) {
            return null;
        }

        List<GameView> list = new ArrayList<GameView>( commonGames.size() );
        for ( CommonGame commonGame : commonGames ) {
            list.add( commonGame2GameView( commonGame ) );
        }

        return list;
    }

    private String commonGameMetaCommonGameMetaCommentaireMove(CommonGame commonGame) {
        if ( commonGame == null ) {
            return null;
        }
        MetaCommonGame metaCommonGame = commonGame.getMetaCommonGame();
        if ( metaCommonGame == null ) {
            return null;
        }
        String metaCommentaireMove = metaCommonGame.getMetaCommentaireMove();
        if ( metaCommentaireMove == null ) {
            return null;
        }
        return metaCommentaireMove;
    }

    private String commonGameMetaCommonGameCommentairePartie(CommonGame commonGame) {
        if ( commonGame == null ) {
            return null;
        }
        MetaCommonGame metaCommonGame = commonGame.getMetaCommonGame();
        if ( metaCommonGame == null ) {
            return null;
        }
        String commentairePartie = metaCommonGame.getCommentairePartie();
        if ( commentairePartie == null ) {
            return null;
        }
        return commentairePartie;
    }

    private long commonGameMetaCommonGameLastSeen(CommonGame commonGame) {
        if ( commonGame == null ) {
            return 0L;
        }
        MetaCommonGame metaCommonGame = commonGame.getMetaCommonGame();
        if ( metaCommonGame == null ) {
            return 0L;
        }
        long lastSeen = metaCommonGame.getLastSeen();
        return lastSeen;
    }

    private boolean commonGameMetaCommonGameFlipBoard(CommonGame commonGame) {
        if ( commonGame == null ) {
            return false;
        }
        MetaCommonGame metaCommonGame = commonGame.getMetaCommonGame();
        if ( metaCommonGame == null ) {
            return false;
        }
        boolean flipBoard = metaCommonGame.isFlipBoard();
        return flipBoard;
    }

    private String commonGameMetaCommonGameSource(CommonGame commonGame) {
        if ( commonGame == null ) {
            return null;
        }
        MetaCommonGame metaCommonGame = commonGame.getMetaCommonGame();
        if ( metaCommonGame == null ) {
            return null;
        }
        String source = metaCommonGame.getSource();
        if ( source == null ) {
            return null;
        }
        return source;
    }

    private String commonGameMetaCommonGameCommentateurPrincipal(CommonGame commonGame) {
        if ( commonGame == null ) {
            return null;
        }
        MetaCommonGame metaCommonGame = commonGame.getMetaCommonGame();
        if ( metaCommonGame == null ) {
            return null;
        }
        String commentateurPrincipal = metaCommonGame.getCommentateurPrincipal();
        if ( commentateurPrincipal == null ) {
            return null;
        }
        return commentateurPrincipal;
    }
}
