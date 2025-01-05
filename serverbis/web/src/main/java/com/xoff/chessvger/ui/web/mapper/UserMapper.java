package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.common.UserTenant;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {


  public abstract UserTenant entity2Dto(User user);

  public abstract List<User> map(List<UserTenant> dtos);

  public abstract User dto2entity(UserTenant dto);

  public abstract List<UserTenant> mapListEntity2Dto(List<User> users);
}