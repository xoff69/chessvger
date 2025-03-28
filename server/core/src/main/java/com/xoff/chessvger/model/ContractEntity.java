package com.xoff.chessvger.model;


import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// TODO virer serialiszble
public class ContractEntity  implements Serializable {

  private Long id;

  private Long price;

  private Integer durationDay;

  private LocalDateTime startDate;

  private LocalDateTime endDate;

  private Long databaseId;

}
