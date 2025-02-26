package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.TenantEntity;

import java.util.Optional;

public interface DatabaseHelperService {
    public Optional<TenantEntity> getFromToken(String token);
    /**
     * create datasource if needed
     * @param token
     * @param schema
     */
    public void setDatasource(String token, String schema);
}
