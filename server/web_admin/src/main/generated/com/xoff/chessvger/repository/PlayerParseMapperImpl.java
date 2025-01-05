package com.xoff.chessvger.repository;

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
public class PlayerParseMapperImpl implements PlayerParseMapper {

    @Override
    public CommonPlayerEntity player2Entity(Player player) {
        if ( player == null ) {
            return null;
        }

        CommonPlayerEntity commonPlayerEntity = new CommonPlayerEntity();

        commonPlayerEntity.setFideId( player.getFideId() );
        commonPlayerEntity.setName( player.getName() );
        commonPlayerEntity.setCountry( player.getCountry() );
        commonPlayerEntity.setSex( player.getSex() );
        commonPlayerEntity.setTitle( player.getTitle() );
        commonPlayerEntity.setWTitle( player.getWTitle() );
        commonPlayerEntity.setOTitle( player.getOTitle() );
        commonPlayerEntity.setFoaTitle( player.getFoaTitle() );
        commonPlayerEntity.setRating( player.getRating() );
        commonPlayerEntity.setGames( player.getGames() );
        commonPlayerEntity.setK( player.getK() );
        commonPlayerEntity.setRapidRating( player.getRapidRating() );
        commonPlayerEntity.setRapidGames( player.getRapidGames() );
        commonPlayerEntity.setRapidK( player.getRapidK() );
        commonPlayerEntity.setBlitzRating( player.getBlitzRating() );
        commonPlayerEntity.setBlitzGames( player.getBlitzGames() );
        commonPlayerEntity.setBlitzK( player.getBlitzK() );
        commonPlayerEntity.setBirthday( player.getBirthday() );
        commonPlayerEntity.setFlag( player.getFlag() );

        return commonPlayerEntity;
    }

    @Override
    public List<CommonPlayerEntity> map(List<Player> players) {
        if ( players == null ) {
            return null;
        }

        List<CommonPlayerEntity> list = new ArrayList<CommonPlayerEntity>( players.size() );
        for ( Player player : players ) {
            list.add( player2Entity( player ) );
        }

        return list;
    }
}
