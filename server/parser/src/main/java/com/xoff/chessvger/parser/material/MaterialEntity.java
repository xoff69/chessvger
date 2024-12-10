package com.xoff.chessvger.parser.material;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialEntity {
  private Long id;

  private long material;

  private List<Long> games;

}
