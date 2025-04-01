package com.xoff.chessvger.database;

import com.xoff.chessvger.model.TenantEntity;

import java.util.Optional;

public interface DatabaseHelperService {
    public Optional<TenantEntity> getFromToken(String token);
    /**
     * create datasource if needed
     * @param token
     * @param schema
     */
    public void setDatasource(String token, String schema);

    public void trace();
}
