package com.canknow.cbp.base.application.applicationService;

import com.canknow.cbp.base.application.dto.*;
import com.canknow.cbp.base.autoMapper.AutoMapper;
import com.canknow.cbp.base.domain.entities.EntityBase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;

public abstract class CrudApplicationService<
        TEntity extends EntityBase,
        TDto extends IDto,
        TGetAllDto extends IDto,
        TGetAllByPageDto extends IDto,
        TFindInput extends FindInput,
        TGetAllInput extends GetAllInput,
        TGetAllByPageInput extends GetAllByPageInput,
        TCreateDto extends CreateInput,
        TUpdateDto extends UpdateInput> extends DeleteAndGetApplicationService<TEntity, TDto, TGetAllDto, TGetAllByPageDto, TFindInput, TGetAllInput, TGetAllByPageInput> {

    protected String CreatePermissionName;
    protected String UpdatePermissionName;

    @Transactional
    public TDto create(@Validated TCreateDto input) {
        this.checkCreatePermission();

        TEntity entity = AutoMapper.mapTo(input, getTClass(0));

        this.beforeCreate(input, entity);
        repository.insert(entity);
        afterCreate(input, entity);

        return AutoMapper.mapTo(entity, getTClass(1));
    }

    protected void checkCreatePermission() {
        checkPermission(CreatePermissionName);
    }

    public void beforeCreate (TCreateDto input, TEntity entity) {

    }

    public void afterCreate(TCreateDto input, TEntity entity) {

    }

    @Transactional
    public void update(@Validated TUpdateDto input) {
        this.checkUpdatePermission();
        TEntity entity = repository.get(input.getId());

        if (entity == null) {
            throw new EntityNotFoundException();
        }
        AutoMapper.mapTo(input, entity);

        this.beforeUpdate(input, entity);
        repository.update(entity);
        afterUpdate(input, entity);
    }

    public void beforeUpdate(TUpdateDto input, TEntity entity) {

    }

    public void afterUpdate(TUpdateDto input,TEntity entity) {

    }

    protected void checkUpdatePermission() {
        checkPermission(UpdatePermissionName);
    }
}
