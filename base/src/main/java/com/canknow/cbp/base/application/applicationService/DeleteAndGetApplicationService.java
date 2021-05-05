package com.canknow.cbp.base.application.applicationService;

import com.canknow.cbp.base.application.dto.*;
import com.canknow.cbp.base.autoMapper.AutoMapper;
import com.canknow.cbp.base.domain.entities.EntityBase;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;

public abstract class DeleteAndGetApplicationService<
        TEntity extends EntityBase,
        TDto extends IDto,
        TGetAllDto extends IDto,
        TGetAllByPageDto extends IDto,
        TFindInput extends FindInput,
        TGetAllInput extends GetAllInput,
        TGetAllByPageInput extends GetAllByPageInput> extends GetApplicationService<TEntity, TDto, TGetAllDto, TGetAllByPageDto, TFindInput, TGetAllInput, TGetAllByPageInput> {
    protected String DeletePermissionName;

    @Transactional
    public void delete(IdInput input) {
        this.checkDeletePermission();
        TEntity entity = this.repository.get(input.getId());

        if (entity == null) {
            throw new EntityNotFoundException();
        }
        this.beforeDelete(entity);
        repository.delete(entity);
        this.afterDelete(input.getId());
    }

    protected void checkDeletePermission() {
        checkPermission(DeletePermissionName);
    }

    public void beforeDelete (TEntity entity) {

    }

    public void afterDelete(String id) {

    }
}
