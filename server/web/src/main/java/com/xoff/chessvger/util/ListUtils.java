package com.xoff.chessvger.util;

import com.xoff.chessvger.ui.Pageable;
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
}
