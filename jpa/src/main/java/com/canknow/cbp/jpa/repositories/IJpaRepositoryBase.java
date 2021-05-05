package com.canknow.cbp.jpa.repositories;

import com.canknow.cbp.base.domain.entities.EntityBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

@NoRepositoryBean
public interface IJpaRepositoryBase<TEntity extends EntityBase> extends
        JpaRepository<TEntity, String>,
        JpaSpecificationExecutor<TEntity>,
        PagingAndSortingRepository<TEntity, String> {
}
