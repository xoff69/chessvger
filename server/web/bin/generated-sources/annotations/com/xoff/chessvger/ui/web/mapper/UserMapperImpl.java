package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.common.UserTenant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-24T21:01:40-0700",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.41.0.z20250115-2156, environment: Java 21.0.5 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public UserTenant entity2Dto(User user) {
        if ( user == null ) {
            return null;
        }

        UserTenant userTenant = new UserTenant();

        userTenant.setIsAdmin( user.getIsAdmin() );
        userTenant.setLogin( user.getLogin() );
        userTenant.setPassword( user.getPassword() );

        return userTenant;
    }

    @Override
    public List<User> map(List<UserTenant> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtos.size() );
        for ( UserTenant userTenant : dtos ) {
            list.add( dto2entity( userTenant ) );
        }

        return list;
    }

    @Override
    public User dto2entity(UserTenant dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setIsAdmin( dto.getIsAdmin() );
        user.setLogin( dto.getLogin() );
        user.setPassword( dto.getPassword() );

        return user;
    }

    @Override
    public List<UserTenant> mapListEntity2Dto(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserTenant> list = new ArrayList<UserTenant>( users.size() );
        for ( User user : users ) {
            list.add( entity2Dto( user ) );
        }

        return list;
    }
}
