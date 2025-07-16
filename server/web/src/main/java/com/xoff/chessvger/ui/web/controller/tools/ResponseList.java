package com.xoff.chessvger.ui.web.controller.tools;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseList<T> {
    private List<T> list;
    private long count;
}
