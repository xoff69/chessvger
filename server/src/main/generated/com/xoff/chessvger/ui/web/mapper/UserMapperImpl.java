package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.ui.web.view.UserDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-29T12:45:21-0600",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl extends UserMapper {

    @Override
    public UserDto entity2Dto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setLogin( user.getLogin() );
        userDto.setName( user.getName() );
        userDto.setPassword( user.getPassword() );
        userDto.setIsAdmin( user.getIsAdmin() );

        return userDto;
    }

    @Override
    public List<User> map(List<UserDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtos.size() );
        for ( UserDto userDto : dtos ) {
            list.add( dto2entity( userDto ) );
        }

        return list;
    }

    @Override
    public User dto2entity(UserDto dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        if ( dto.getId() != null ) {
            user.setId( dto.getId() );
        }
        user.setLogin( dto.getLogin() );
        user.setPassword( dto.getPassword() );
        user.setName( dto.getName() );
        user.setIsAdmin( dto.getIsAdmin() );

        return user;
    }

    @Override
    public List<UserDto> mapListEntity2Dto(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( users.size() );
        for ( User user : users ) {
            list.add( entity2Dto( user ) );
        }

        return list;
    }
}
