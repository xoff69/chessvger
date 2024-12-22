package com.xoff.chessvger.common;

import java.time.Instant;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Contract {
  private long price;
  private int durationDay;
  private Instant startDate;
  private Instant endDate;
  private long databaseId;
}
