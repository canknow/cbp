package com.canknow.cbp.base.domain.repositories;

import com.canknow.cbp.base.domain.entities.EntityBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface IRepository<TEntity extends EntityBase> {
    boolean existsById(String var1);

    TEntity get(String id);

    TEntity firstOrDefault(Specification<TEntity> specification);

    long count();

    long count(Specification<TEntity> specification);

    Page<TEntity> getAllByPage(Pageable pageable, Specification<TEntity> specification);

    List<TEntity> getAll(Specification<TEntity> specification);

    List<TEntity> getAll(Specification<TEntity> specification, Sort sort);

    List<TEntity> getAll();

    List<TEntity> getAll(Sort sort);

    void insert(TEntity entity);

    void update(TEntity entity);

    void updateAll(List<TEntity> entities);

    void delete(String id);

    void delete(TEntity entity);

    void deleteAll();

    void deleteAll(Iterable<? extends TEntity> entities);

    void deleteInBatch(Iterable<TEntity> entities);

    void deleteAllInBatch();
}
