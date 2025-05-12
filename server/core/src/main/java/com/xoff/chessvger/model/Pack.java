package com.xoff.chessvger.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class Pack  {
    @Serial
    private static final long serialVersionUID = 5730267369063215547L;

    private String name;
    private long startDate;
    private long endDate;
    private double price;




}
