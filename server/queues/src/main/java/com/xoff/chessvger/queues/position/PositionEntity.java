package com.xoff.chessvger.queues.position;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionEntity {
  private Long id;

  private long position;
  private List<Long> games;

}
