package com.xoff.chessvger.view;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FeatureDto {
  private Long id;
  private String name;

  private String enabled;
}
