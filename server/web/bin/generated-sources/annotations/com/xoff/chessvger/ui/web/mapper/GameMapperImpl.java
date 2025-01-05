package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.chess.game.MetaCommonGame;
import com.xoff.chessvger.view.GameView;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-05T14:19:58-0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
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
        gameView.setBlackElo( commonGame.getBlackElo() );
        gameView.setBlackTitle( commonGame.getBlackTitle() );
        gameView.setDate( commonGame.getDate() );
        gameView.setDeleted( commonGame.isDeleted() );
        gameView.setEco( commonGame.getEco() );
        gameView.setEvent( commonGame.getEvent() );
        gameView.setEventDate( commonGame.getEventDate() );
        gameView.setFavori( commonGame.isFavori() );
        gameView.setFirstMove( commonGame.getFirstMove() );
        gameView.setId( (int) commonGame.getId() );
        gameView.setInformationsFaitDeJeu( commonGame.getInformationsFaitDeJeu() );
        gameView.setInteret( commonGame.getInteret() );
        gameView.setLastPosition( (int) commonGame.getLastPosition() );
        gameView.setMoves( commonGame.getMoves() );
        gameView.setNbcoups( commonGame.getNbcoups() );
        gameView.setOpening( commonGame.getOpening() );
        gameView.setPartieAnalysee( commonGame.isPartieAnalysee() );
        gameView.setRound( commonGame.getRound() );
        gameView.setSite( commonGame.getSite() );
        gameView.setTheorique( commonGame.isTheorique() );
        gameView.setWhiteElo( commonGame.getWhiteElo() );
        gameView.setWhiteTitle( commonGame.getWhiteTitle() );

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

        commonGame.setBlackElo( gameView.getBlackElo() );
        commonGame.setBlackTitle( gameView.getBlackTitle() );
        commonGame.setDate( gameView.getDate() );
        commonGame.setDeleted( gameView.isDeleted() );
        commonGame.setEco( gameView.getEco() );
        commonGame.setEvent( gameView.getEvent() );
        commonGame.setEventDate( gameView.getEventDate() );
        commonGame.setFavori( gameView.isFavori() );
        commonGame.setFirstMove( gameView.getFirstMove() );
        commonGame.setId( gameView.getId() );
        commonGame.setInformationsFaitDeJeu( gameView.getInformationsFaitDeJeu() );
        commonGame.setInteret( gameView.getInteret() );
        commonGame.setLastPosition( gameView.getLastPosition() );
        if ( gameView.getLastUpdate() != null ) {
            commonGame.setLastUpdate( Long.parseLong( gameView.getLastUpdate() ) );
        }
        commonGame.setMoves( gameView.getMoves() );
        commonGame.setNbcoups( gameView.getNbcoups() );
        commonGame.setOpening( gameView.getOpening() );
        commonGame.setPartieAnalysee( gameView.isPartieAnalysee() );
        commonGame.setRound( gameView.getRound() );
        commonGame.setSite( gameView.getSite() );
        commonGame.setTheorique( gameView.isTheorique() );
        commonGame.setWhiteElo( gameView.getWhiteElo() );
        commonGame.setWhiteTitle( gameView.getWhiteTitle() );

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
