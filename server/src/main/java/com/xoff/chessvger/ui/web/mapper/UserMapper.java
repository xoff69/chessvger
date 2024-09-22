package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.ui.web.view.UserDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {


  public abstract UserDto entity2Dto(User user);

  public abstract List<User> map(List<UserDto> dtos);

  public abstract User dto2entity(UserDto dto);

  public abstract List<UserDto> mapListEntity2Dto(List<User> users);
}