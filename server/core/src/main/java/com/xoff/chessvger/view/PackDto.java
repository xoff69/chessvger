package com.xoff.chessvger.view;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PackDto {
  private Long id;
  private String name;
  private long startDate;
  private long endDate;
  private double price;
}
