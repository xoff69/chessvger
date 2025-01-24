package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.userpack.UserPack;
import com.xoff.chessvger.view.UserPackDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:58:43-0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class UserPackMapperImpl extends UserPackMapper {

    @Override
    public UserPackDto entity2Dto(UserPack userPack) {
        if ( userPack == null ) {
            return null;
        }

        UserPackDto userPackDto = new UserPackDto();

        userPackDto.setId( userPack.getId() );

        return userPackDto;
    }

    @Override
    public List<UserPack> map(List<UserPackDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<UserPack> list = new ArrayList<UserPack>( dtos.size() );
        for ( UserPackDto userPackDto : dtos ) {
            list.add( dto2entity( userPackDto ) );
        }

        return list;
    }

    @Override
    public UserPack dto2entity(UserPackDto dto) {
        if ( dto == null ) {
            return null;
        }

        UserPack userPack = new UserPack();

        if ( dto.getId() != null ) {
            userPack.setId( dto.getId() );
        }

        return userPack;
    }

    @Override
    public List<UserPackDto> mapListEntity2Dto(List<UserPack> usersPack) {
        if ( usersPack == null ) {
            return null;
        }

        List<UserPackDto> list = new ArrayList<UserPackDto>( usersPack.size() );
        for ( UserPack userPack : usersPack ) {
            list.add( entity2Dto( userPack ) );
        }

        return list;
    }
}
