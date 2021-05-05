package com.canknow.cbp.jpa.repositories;

import com.canknow.cbp.base.domain.entities.EntityBase;
import com.canknow.cbp.base.domain.entities.IIHasUserId;
import com.canknow.cbp.base.domain.event.entities.EntityChangeEventHelper;
import com.canknow.cbp.base.domain.repositories.IRepository;
import com.canknow.cbp.base.runtime.session.IApplicationSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public abstract class JpaRepositoryBase<TEntity extends EntityBase, TJpaRepository extends IJpaRepositoryBase<TEntity>> implements IRepository<TEntity> {
    protected TJpaRepository jpaRepository;

    @Autowired
    private EntityChangeEventHelper entityChangeEventHelper;

    @Autowired
    private IApplicationSession applicationSession;

    public JpaRepositoryBase(TJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public boolean existsById(String id) {
        return jpaRepository.existsById(id);
    }

    @Override
    public TEntity get(String id) {
        return jpaRepository.getOne(id);
    }

    @Override
    public TEntity firstOrDefault(Specification<TEntity> specification) {
        Optional<TEntity> entityOptional = jpaRepository.findOne(specification);

        return entityOptional.orElse(null);
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    @Override
    public long count(Specification<TEntity> specification) {
        return jpaRepository.count(specification);
    }

    @Override
    public Page<TEntity> getAllByPage(Pageable pageable, Specification<TEntity> specification) {
        return jpaRepository.findAll(specification, pageable);
    }

    @Override
    public List<TEntity> getAll(Specification<TEntity> specification) {
        return jpaRepository.findAll(specification);
    }

    @Override
    public List<TEntity> getAll(Specification<TEntity> specification, Sort sort) {
        return jpaRepository.findAll(specification, sort);
    }

    @Override
    public List<TEntity> getAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<TEntity> getAll(Sort sort) {
        return jpaRepository.findAll(sort);
    }

    @Override
    public void insert(TEntity entity) {
        if (entity instanceof IIHasUserId) {
            ((IIHasUserId) entity).setUserId(applicationSession.getUserId());
        }
        jpaRepository.save(entity);
        entityChangeEventHelper.triggerEntityCreatedEvent(entity);
    }

    @Override
    public void update(TEntity entity) {
        jpaRepository.save(entity);
        entityChangeEventHelper.triggerEntityUpdatedEvent(entity);
    }

    @Override
    public void updateAll(List<TEntity> entities) {
        jpaRepository.saveAll(entities);
    }

    @Override
    public void delete(String id) {
        TEntity entity = jpaRepository.getOne(id);
        delete(entity);
    }

    @Override
    public void delete(TEntity entity) {
        jpaRepository.delete(entity);
        entityChangeEventHelper.triggerEntityDeletedEvent(entity);
    }

    @Override
    public void deleteAll() {
        jpaRepository.deleteAllInBatch();
    }

    @Override
    public void deleteAll(Iterable<? extends TEntity> entities) {
        jpaRepository.deleteAll(entities);
    }

    @Override
    public void deleteInBatch(Iterable<TEntity> entities) {
        jpaRepository.deleteInBatch(entities);
    }

    @Override
    public void deleteAllInBatch() {
        jpaRepository.deleteAllInBatch();
    }
}
