package com.xoff.chessvger.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
public class Contract {
    private long price;
    private int durationDay;
    private Instant startDate;
    private Instant endDate;
    private long databaseId;
}
