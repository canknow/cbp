package com.canknow.cbp.jpa.repositories;

import com.canknow.cbp.base.domain.entities.EntityBase;
import com.canknow.cbp.base.domain.entities.audit.IFullAudited;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityExistsException;
import java.util.List;
import java.util.Optional;

public abstract class FullAuditedJpaRepositoryBase<TEntity extends EntityBase & IFullAudited, TJpaRepository extends IJpaRepositoryBase<TEntity>>
        extends JpaRepositoryBase<TEntity, TJpaRepository> {
    public FullAuditedJpaRepositoryBase(TJpaRepository jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public TEntity get(String id) {
        TEntity entity = jpaRepository.getOne(id);

        if (entity.isDeleted()) {
            throw new EntityExistsException();
        }
        return entity;
    }

    @Override
    public TEntity firstOrDefault(Specification<TEntity> specification) {
        Optional<TEntity> entityOptional = jpaRepository.findOne(specification);

        if (entityOptional.isPresent()) {
            if (entityOptional.get().isDeleted()) {
                throw new EntityExistsException();
            }
            return entityOptional.get();
        }
        return null;
    }

    @Override
    public long count(Specification<TEntity> specification) {
        return jpaRepository.count(andSoftDeleteSpecification(specification));
    }

    protected Specification<TEntity> andSoftDeleteSpecification(Specification<TEntity> specification) {
        return specification.and((Specification<TEntity>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.notEqual(root.get("isDeleted"), 1));
    }

    @Override
    public Page<TEntity> getAllByPage(Pageable pageable, Specification<TEntity> specification) {
        return jpaRepository.findAll(andSoftDeleteSpecification(specification), pageable);
    }

    @Override
    public List<TEntity> getAll(Specification<TEntity> specification) {
        return jpaRepository.findAll(andSoftDeleteSpecification(specification));
    }

    @Override
    public void insert(TEntity entity) {
        jpaRepository.save(entity);
    }

    @Override
    public void update(TEntity entity) {
        jpaRepository.save(entity);
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
        entity.setDeleted(true);
        jpaRepository.save(entity);
    }

    @Override
    public void deleteAll() {
        List<TEntity> entities = jpaRepository.findAll();

        entities.forEach(entity -> {
            entity.setDeleted(true);
        });
        jpaRepository.saveAll(entities);
    }
}
