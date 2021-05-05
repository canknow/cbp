package com.canknow.cbp.web.adapter.inbound.web.controllers;

import com.canknow.cbp.base.application.applicationService.CrudApplicationService;
import com.canknow.cbp.base.application.dto.*;
import com.canknow.cbp.base.auditLog.annotation.Audit;
import com.canknow.cbp.base.domain.entities.EntityBase;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

public abstract class CrudController<
        TEntity extends EntityBase,
        TDto extends IDto,
        TGetAllDto extends IDto,
        TGetAllByPageDto extends IDto,
        TFindInput extends FindInput,
        TGetAllInput extends GetAllInput,
        TGetAllByPageInput extends GetAllByPageInput,
        TCreateDto extends CreateInput,
        TUpdateDto extends UpdateInput,
        TCrudApplicationService extends CrudApplicationService<TEntity, TDto, TGetAllDto, TGetAllByPageDto, TFindInput, TGetAllInput, TGetAllByPageInput, TCreateDto, TUpdateDto>>
        extends DeleteAndGetController<TEntity, TDto, TGetAllDto, TGetAllByPageDto, TFindInput, TGetAllInput, TGetAllByPageInput, TCrudApplicationService> {

    @PutMapping("/create")
    @Audit
    public TDto create(@RequestBody @Validated TCreateDto input) {
        return applicationService.create(input);
    }

    @PostMapping("/update")
    @Audit
    public void update(@RequestBody @Validated TUpdateDto input) {
        applicationService.update(input);
    }
}
