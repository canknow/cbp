package com.canknow.cbp.web.adapter.inbound.web.controllers;

import com.canknow.cbp.base.application.applicationService.GetApplicationService;
import com.canknow.cbp.base.application.dto.*;
import com.canknow.cbp.base.auditLog.annotation.Audit;
import com.canknow.cbp.base.domain.entities.EntityBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

public abstract class GetController<
        TEntity extends EntityBase,
        TDto extends IDto,
        TGetAllDto extends IDto,
        TGetAllByPageDto extends IDto,
        TFindInput extends FindInput,
        TGetAllInput extends GetAllInput,
        TGetAllByPageInput extends GetAllByPageInput,
        TGetApplicationService extends GetApplicationService<TEntity, TDto, TGetAllDto, TGetAllByPageDto, TFindInput, TGetAllInput, TGetAllByPageInput>> extends ControllerBase {

    @Autowired
    protected TGetApplicationService applicationService;

    @GetMapping("/get")
    @Audit
    public TDto get (@RequestParam("id") String id) {
        return applicationService.get(id);
    }

    @GetMapping("/find")
    @Audit
    public TDto find (TFindInput input) {
        return applicationService.find(input);
    }

    @GetMapping("/getAll")
    @Audit
    public ListResultDto<TGetAllDto> getAll (TGetAllInput input) {
        return applicationService.getAll(input);
    }

    @GetMapping("/getAllByPage")
    @Audit
    public PagedResultDto<TGetAllByPageDto> getAllByPage (TGetAllByPageInput input) {
        return applicationService.getAllByPage(input);
    }
}
