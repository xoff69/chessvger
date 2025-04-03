package com.xoff.chessvger.common;


import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.io.Serializable;


public class AdbCommonKeyString<T extends Serializable> extends AdbCommon<String, T> {


    public AdbCommonKeyString(String filename) {
        super(filename);
    }

    public AdbCommonKeyString(String filename, boolean inMemory) {
        super(filename, inMemory);
    }

    public HTreeMap<String, T> makeMap() {
        HTreeMap<String, T> map = db.hashMap(getFilename() + "_map").keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.JAVA).createOrOpen();
        return map;
    }
}
