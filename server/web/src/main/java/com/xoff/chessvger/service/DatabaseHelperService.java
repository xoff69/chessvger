package com.xoff.chessvger.service;

public interface DatabaseHelperService {

    /**
     * create datasource if needed
     * @param token
     * @param schema
     */
    public void setDatasource(String token, String schema);
}
