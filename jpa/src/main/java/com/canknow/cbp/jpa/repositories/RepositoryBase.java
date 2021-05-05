package com.canknow.cbp.jpa.repositories;

import com.canknow.cbp.base.domain.entities.EntityBase;
import com.canknow.cbp.base.domain.entities.IIHasUserId;
import com.canknow.cbp.base.domain.repositories.IRepository;
import com.canknow.cbp.base.runtime.session.IApplicationSession;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.data.util.ProxyUtils;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.lang.reflect.ParameterizedType;
import java.util.*;

import static org.springframework.data.jpa.repository.query.QueryUtils.*;

public class RepositoryBase<TEntity extends EntityBase> implements IRepository<TEntity> {
    @PersistenceContext
    private EntityManager entityManager;

    private final JpaEntityInformation<TEntity, ?> entityInformation;

    private final PersistenceProvider provider;

    @Autowired
    private IApplicationSession applicationSession;

    public RepositoryBase() {
        this.entityInformation = getEntityInformation();
        this.provider = PersistenceProvider.fromEntityManager(entityManager);
    }

    @Override
    public boolean existsById(String id) {
        if (entityInformation.getIdAttribute() == null) {
            return findById(id).isPresent();
        }

        String placeholder = provider.getCountQueryPlaceholder();
        String entityName = entityInformation.getEntityName();
        Iterable<String> idAttributeNames = entityInformation.getIdAttributeNames();
        String existsQuery = QueryUtils.getExistsQueryString(entityName, placeholder, idAttributeNames);

        TypedQuery<Long> query = entityManager.createQuery(existsQuery, Long.class);

        if (!entityInformation.hasCompositeId()) {
            query.setParameter(idAttributeNames.iterator().next(), id);
            return query.getSingleResult() == 1L;
        }

        for (String idAttributeName : idAttributeNames) {
            Object idAttributeValue = entityInformation.getCompositeIdAttributeValue(id, idAttributeName);

            boolean complexIdParameterValueDiscovered = idAttributeValue != null
                    && !query.getParameter(idAttributeName).getParameterType().isAssignableFrom(idAttributeValue.getClass());

            if (complexIdParameterValueDiscovered) {

                // fall-back to findById(id) which does the proper mapping for the parameter.
                return findById(id).isPresent();
            }

            query.setParameter(idAttributeName, idAttributeValue);
        }

        return query.getSingleResult() == 1L;
    }

    public Optional<TEntity> findById(String id) {
        return findById(id, null, null);
    }

    public Optional<TEntity> findById(String id, LockModeType type, Map<String, Object> hints) {

        Class<TEntity> domainType = getDomainClass();

        if (type == null && hints == null) {
            return Optional.ofNullable(entityManager.find(domainType, id));
        }
        return Optional.ofNullable(type == null ? entityManager.find(domainType, id, hints) : entityManager.find(domainType, id, type, hints));
    }

    @Override
    public TEntity get(String id) {
        return get(id, null, null);
    }

    public TEntity get(String id, LockModeType type, Map<String, Object> hints) {
        Class<TEntity> domainType = getDomainClass();

        if (type == null && hints == null) {
            return entityManager.find(domainType, id);
        }
        return type == null ? entityManager.find(domainType, id, hints) : entityManager.find(domainType, id, type, hints);
    }

    @Override
    public TEntity firstOrDefault(Specification<TEntity> specification) {
        try {
            return getQuery(specification, Sort.unsorted()).getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public long count() {
        return entityManager.createQuery(getCountQueryString(), Long.class).getSingleResult();
    }

    private String getCountQueryString() {
        String countQuery = String.format(COUNT_QUERY_STRING, provider.getCountQueryPlaceholder(), "%s");
        return getQueryString(countQuery, entityInformation.getEntityName());
    }

    public <T, ID> JpaEntityInformation<T, ID> getEntityInformation() {
        return (JpaEntityInformation<T, ID>) JpaEntityInformationSupport.getEntityInformation(getDomainClass(), entityManager);
    }

    @Override
    public long count(Specification<TEntity> specification) {
        return executeCountQuery(getCountQuery(specification, getDomainClass()));
    }

    protected <S extends TEntity> TypedQuery<Long> getCountQuery(@Nullable Specification<S> specification, Class<S> domainClass) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<S> root = applySpecificationToCriteria(specification, domainClass, query);

        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        }
        else {
            query.select(builder.count(root));
        }

        // Remove all Orders the Specifications might have applied
        query.orderBy(Collections.<Order> emptyList());

        return entityManager.createQuery(query);
    }

    private static long executeCountQuery(TypedQuery<Long> query) {

        Assert.notNull(query, "TypedQuery must not be null!");

        List<Long> totals = query.getResultList();
        long total = 0L;

        for (Long element : totals) {
            total += element == null ? 0 : element;
        }

        return total;
    }

    @Override
    public Page<TEntity> getAllByPage(Pageable pageable, Specification<TEntity> specification) {
        TypedQuery<TEntity> query = getQuery(specification, pageable);
        return isUnPaged(pageable) ? new PageImpl<TEntity>(query.getResultList()) : readPage(query, getDomainClass(), pageable, specification);
    }

    private static boolean isUnPaged(Pageable pageable) {
        return pageable.isUnpaged();
    }

    protected <S extends TEntity> Page<S> readPage(TypedQuery<S> query, final Class<S> domainClass, Pageable pageable, @Nullable Specification<S> specification) {

        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

        return PageableExecutionUtils.getPage(query.getResultList(), pageable, () -> executeCountQuery(getCountQuery(specification, domainClass)));
    }

    @Override
    public List<TEntity> getAll(Specification<TEntity> specification) {
        return getQuery(specification, Sort.unsorted()).getResultList();
    }

    @Override
    public List<TEntity> getAll(Specification<TEntity> specification, Sort sort) {
        return getQuery(specification, sort).getResultList();
    }

    @Override
    public List<TEntity> getAll() {
        return getQuery(null, Sort.unsorted()).getResultList();
    }

    protected TypedQuery<TEntity> getQuery(@Nullable Specification<TEntity> specification, Sort sort) {
        return getQuery(specification, getDomainClass(), sort);
    }

    protected TypedQuery<TEntity> getQuery(@Nullable Specification<TEntity> specification, Pageable pageable) {
        Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
        return getQuery(specification, getDomainClass(), sort);
    }

    protected <S extends TEntity> TypedQuery<S> getQuery(@Nullable Specification<S> specification, Class<S> domainClass, Sort sort) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<S> query = builder.createQuery(domainClass);

        Root<S> root = applySpecificationToCriteria(specification, domainClass, query);
        query.select(root);

        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }
        return entityManager.createQuery(query);
    }

    private <S, U extends TEntity> Root<U> applySpecificationToCriteria(@Nullable Specification<U> specification, Class<U> domainClass, CriteriaQuery<S> query) {
        Assert.notNull(domainClass, "Domain class must not be null!");
        Assert.notNull(query, "CriteriaQuery must not be null!");

        Root<U> root = query.from(domainClass);

        if (specification == null) {
            return root;
        }

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Predicate predicate = specification.toPredicate(root, query, builder);

        if (predicate != null) {
            query.where(predicate);
        }

        return root;
    }

    public TEntity getWithGraph(String id, String graphName) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph(graphName);
        Map<String, Object> properties = new HashMap<>();
        properties.put("javax.persistence.fetchgraph", entityGraph);
        return entityManager.find(getDomainClass(), id, properties);
    }

    @Override
    public List<TEntity> getAll(Sort sort) {
        return null;
    }

    @Transactional
    public <S extends TEntity> S save(S entity) {
        if (entityInformation.isNew(entity)) {
            entityManager.persist(entity);
            return entity;
        }
        else {
            return entityManager.merge(entity);
        }
    }

    @Override
    public void insert(TEntity entity) {
        if (entity instanceof IIHasUserId) {
            ((IIHasUserId) entity).setUserId(applicationSession.getUserId());
        }
        save(entity);
    }

    @Override
    @Transactional(readOnly = false)
    public void update(TEntity entity) {
        save(entity);
    }

    @Override
    public void updateAll(List<TEntity> entities) {
        Assert.notNull(entities, "Entities must not be null!");

        for (TEntity entity : entities) {
            save(entity);
        }
    }

    @Override
    public void delete(String id) {
        delete(findById(id).orElseThrow(() -> new EmptyResultDataAccessException(String.format("No %s entity with id %s exists!", entityInformation.getJavaType(), id), 1)));
    }

    @Override
    public void delete(TEntity entity) {
        Assert.notNull(entity, "Entity must not be null!");

        if (entityInformation.isNew(entity)) {
            return;
        }
        Class<?> type = ProxyUtils.getUserClass(entity);

        TEntity existing = (TEntity) entityManager.find(type, entityInformation.getId(entity));

        // if the entity to be deleted doesn't exist, delete is a NOOP
        if (existing == null) {
            return;
        }

        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Override
    public void deleteAll() {
        for (TEntity element : getAll()) {
            delete(element);
        }
    }

    @Override
    public void deleteAll(Iterable<? extends TEntity> entities) {
        Assert.notNull(entities, "Entities must not be null!");

        for (TEntity entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteInBatch(Iterable<TEntity> entities) {
        Assert.notNull(entities, "Entities must not be null!");

        if (!entities.iterator().hasNext()) {
            return;
        }

        applyAndBind(getQueryString(DELETE_ALL_QUERY_STRING, entityInformation.getEntityName()), entities, entityManager).executeUpdate();
    }

    @Override
    public void deleteAllInBatch() {
        for (TEntity element : getAll()) {
            delete(element);
        }
    }

    public List<Map<String, Object>> executeNativeSqlQuery(String sql, Map<String, Object> params, Integer firstResult, Integer maxResult) {
        Query query = entityManager.createNativeQuery(sql);
        setParameters(sql, params, query);

        if (firstResult != null) {
            query.setFirstResult(firstResult);
        }
        else if (maxResult != null) {
            query.setMaxResults(maxResult);
        }
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    public int executeNativeSqlQueryCount(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        setParameters(sql, params, query);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

        List<Map<String, Object>> list = query.getResultList();

        if (CollectionUtils.isEmpty(list)) {
            return 0;
        }
        return list.size();
    }

    public <T> List<T> executeQuery(String sql, Map<String, Object> params, Integer firstResult, Integer maxResult) {
        TypedQuery<T> query = entityManager.createQuery(sql, getDomainClass());
        setParameters(sql, params, query);

        if (firstResult != null) {
            query.setFirstResult(firstResult);
        }
        else if (maxResult != null) {
            query.setMaxResults(maxResult);
        }
        return query.getResultList();
    }

    public int executeSqlUpdate(String sql, Map<String, Object> params) {
        Query query = entityManager.createNativeQuery(sql);
        setParameters(sql, params, query);

        return query.executeUpdate();
    }

    private void setParameters(String sql, Map<String, Object> params, Query query) {
        if (params != null && !params.isEmpty()) {

            for (Map.Entry<String, Object> entry : params.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (sql.contains(":" + key)) {
                    query.setParameter(key, value);
                }
            }
        }
    }

    protected <T> Class<T> getDomainClass() {
        return (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}