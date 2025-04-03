package com.xoff.chessvger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PositionEntity {
    private Long id;

    private long position;

    private List<Long> games;

}
