package com.xoff.chessvger;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseList<T> {
  private List<T> list;
  private long count;
}
