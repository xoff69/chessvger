package com.xoff.chessvger;

import com.xoff.chessvger.chess.game.CommonGame;
import com.xoff.chessvger.ui.web.mapper.GameMapper;
import com.xoff.chessvger.ui.web.navigation.Page;
import com.xoff.chessvger.util.Pageable;
import com.xoff.chessvger.view.PageView;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListUtils {
  public    static<T>  List<T> sublist(List<T> list, Pageable pageable){
    log.info("listutils "+list.size()+" "+pageable);
    int len=list.size();
    int max=Math.min(len,(pageable.pageNumber-1)*pageable.pageSize+pageable.pageSize);
    log.info("listutils 2--"+list.subList((pageable.pageNumber-1)*pageable.pageSize,max));
    return list.subList((pageable.pageNumber-1)*pageable.pageSize,max);
  }
  public static PageView<CommonGame> computePaging(List<CommonGame> commonGames, java.awt.print.Pageable paging) {
    PageView<CommonGame> pageGameView =
        Page.compute(paging, GameMapper.INSTANCE.mapListEntity2Dto(commonGames));

    return pageGameView;

  }
}
