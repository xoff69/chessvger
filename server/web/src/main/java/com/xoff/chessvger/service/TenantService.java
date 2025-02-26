package com.xoff.chessvger.service;

import com.xoff.chessvger.repository.TenantEntity;

public interface TenantService {
    public TenantEntity getTenant(long tenantId);
    public TenantEntity getByUserId(long userId);
}
