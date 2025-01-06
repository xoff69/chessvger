package com.xoff.chessvger.ui.web.mapper;

import com.xoff.chessvger.chess.user.User;
import com.xoff.chessvger.chess.userpack.UserPack;
import com.xoff.chessvger.view.UserPackDto;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserPackMapper {


  public abstract UserPackDto entity2Dto(UserPack userPack);

  public abstract List<UserPack> map(List<UserPackDto> dtos);

  public abstract UserPack dto2entity(UserPackDto dto);

  public abstract List<UserPackDto> mapListEntity2Dto(List<UserPack> usersPack);
}