package com.canknow.cbp.web.adapter.inbound.web.controllers;

import com.canknow.cbp.base.application.applicationService.DeleteAndGetApplicationService;
import com.canknow.cbp.base.application.dto.*;
import com.canknow.cbp.base.auditLog.annotation.Audit;
import com.canknow.cbp.base.domain.entities.EntityBase;
import org.springframework.web.bind.annotation.DeleteMapping;

public abstract class DeleteAndGetController<
        TEntity extends EntityBase,
        TDto extends IDto,
        TGetAllDto extends IDto,
        TGetAllByPageDto extends IDto,
        TFindInput extends FindInput,
        TGetAllInput extends GetAllInput,
        TGetAllByPageInput extends GetAllByPageInput,
        TCrudApplicationService extends DeleteAndGetApplicationService<TEntity, TDto, TGetAllDto, TGetAllByPageDto, TFindInput, TGetAllInput, TGetAllByPageInput>>
        extends GetController<TEntity, TDto, TGetAllDto, TGetAllByPageDto, TFindInput, TGetAllInput, TGetAllByPageInput, TCrudApplicationService> {

    @DeleteMapping("/delete")
    @Audit
    public void delete(IdInput input) {
        applicationService.delete(input);
    }
}
