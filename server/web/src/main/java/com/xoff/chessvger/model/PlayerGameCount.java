package com.xoff.chessvger.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerGameCount {
        private Long id;
        private String name;
        private int gameCount;
}
