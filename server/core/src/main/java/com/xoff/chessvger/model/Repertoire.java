package com.xoff.chessvger.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;

@Data
@NoArgsConstructor
public class Repertoire {
    @Serial
    private static final long serialVersionUID = 5424741731756895241L;

    private long userId;
    private long databaseId;



}
