package com.canknow.cbp.common.application.organizationUnitUser;

import com.canknow.cbp.base.application.dto.CreateInput;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrganizationUnitUserCreateInput extends CreateInput {
    @NotEmpty
    private String userId;

    @NotEmpty
    private String organizationUnitId;
}
