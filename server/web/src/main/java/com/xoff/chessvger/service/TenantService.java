package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.TenantEntity;

import java.util.Optional;

public interface TenantService {
    public Optional<TenantEntity> getTenant(long tenantId);
    public Optional<TenantEntity> getByUserId(long userId);
}
