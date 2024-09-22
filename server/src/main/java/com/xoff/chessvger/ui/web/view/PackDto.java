package com.xoff.chessvger.ui.web.view;

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
