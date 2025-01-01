package com.xoff.chessvger.repository;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-01T07:53:23-0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.v20241217-1506, environment: Java 17.0.13 (Eclipse Adoptium)"
)
@Component
public class PlayerParseMapperImpl implements PlayerParseMapper {

    @Override
    public CommonPlayerEntity player2Entity(Player player) {
        if ( player == null ) {
            return null;
        }

        CommonPlayerEntity commonPlayerEntity = new CommonPlayerEntity();

        commonPlayerEntity.setBirthday( player.getBirthday() );
        commonPlayerEntity.setBlitzGames( player.getBlitzGames() );
        commonPlayerEntity.setBlitzK( player.getBlitzK() );
        commonPlayerEntity.setBlitzRating( player.getBlitzRating() );
        commonPlayerEntity.setCountry( player.getCountry() );
        commonPlayerEntity.setFideId( player.getFideId() );
        commonPlayerEntity.setFlag( player.getFlag() );
        commonPlayerEntity.setFoaTitle( player.getFoaTitle() );
        commonPlayerEntity.setGames( player.getGames() );
        commonPlayerEntity.setK( player.getK() );
        commonPlayerEntity.setName( player.getName() );
        commonPlayerEntity.setOTitle( player.getOTitle() );
        commonPlayerEntity.setRapidGames( player.getRapidGames() );
        commonPlayerEntity.setRapidK( player.getRapidK() );
        commonPlayerEntity.setRapidRating( player.getRapidRating() );
        commonPlayerEntity.setRating( player.getRating() );
        commonPlayerEntity.setSex( player.getSex() );
        commonPlayerEntity.setTitle( player.getTitle() );
        commonPlayerEntity.setWTitle( player.getWTitle() );

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
