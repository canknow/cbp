package com.canknow.cbp.base.application.applicationService;

import com.canknow.cbp.base.application.dto.*;
import com.canknow.cbp.base.autoMapper.AutoMapper;
import com.canknow.cbp.base.domain.entities.EntityBase;
import com.canknow.cbp.base.domain.repositories.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public abstract class GetApplicationService<
        TEntity extends EntityBase,
        TGetDto extends IDto,
        TGetAllDto extends IDto,
        TGetAllByPageDto extends IDto,
        TFindInput extends FindInput,
        TGetAllInput extends GetAllInput,
        TGetAllByPageInput extends GetAllByPageInput> extends ApplicationService {

    @Autowired
    protected IRepository<TEntity> repository;

    protected String GetPermissionName;
    protected String GetAllPermissionName;

    protected Specification<TEntity> createFindSpecification(TFindInput input) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate[] predicateArray = new Predicate[predicates.size()];
            return criteriaBuilder.and(predicates.toArray(predicateArray));
        };
    }

    protected Specification<TEntity> createGetAllByPageSpecification(TGetAllByPageInput input) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate[] predicateArray = new Predicate[predicates.size()];
            return criteriaBuilder.and(predicates.toArray(predicateArray));
        };
    }

    protected Specification<TEntity> createGetAllSpecification(TGetAllInput input) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Predicate[] predicateArray = new Predicate[predicates.size()];
            return criteriaBuilder.and(predicates.toArray(predicateArray));
        };
    }

    public TGetDto get(String id) {
        this.checkGetPermission();
        TEntity model = repository.get(id);
        return AutoMapper.mapTo(model, getTClass(1));
    }

    public TGetDto find(TFindInput input) {
        this.checkGetPermission();
        TEntity model = repository.firstOrDefault(createFindSpecification(input));
        return AutoMapper.mapTo(model, getTClass(1));
    }

    protected void checkGetPermission() {
        checkPermission(GetPermissionName);
    }

    public ListResultDto<TGetAllDto> getAll (TGetAllInput input) {
        this.checkGetAllPermissions();
        List<TEntity> entities = repository.getAll(createGetAllSpecification(input), getSort(input));
        List<TGetAllDto> dtos = AutoMapper.mapListTo(entities, getTClass(2));
        return new ListResultDto<>(dtos);
    }

    protected void checkGetAllPermissions() {
        checkPermission(GetAllPermissionName);
    }

    protected Sort getSort(GetAllInput getAllInput) {
        return getAllInput.buildSort();
    }

    public PagedResultDto<TGetAllByPageDto> getAllByPage (TGetAllByPageInput input) {
        this.checkGetAllPermissions();
        Sort sort = getSort(input);
        PageRequest pageable = PageRequest.of(input.getPageIndex(), input.getPageSize(), sort);
        Page<TEntity> page = repository.getAllByPage(pageable, createGetAllByPageSpecification(input));
        List<TEntity> entities = page.getContent();
        List<TGetAllByPageDto> dtos = AutoMapper.mapListTo(entities, getTClass(3));
        return new PagedResultDto<>(dtos, page.getTotalElements());
    }
}
