package com.xoff.chessvger.repository;

import org.springframework.data.repository.CrudRepository;

public interface TenantRepository  extends CrudRepository<TenantEntity, Long> {
    public TenantEntity findbyid(Long id);
}
