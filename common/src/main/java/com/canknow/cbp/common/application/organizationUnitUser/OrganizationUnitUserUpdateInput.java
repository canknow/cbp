package com.canknow.cbp.common.application.organizationUnitUser;

import com.canknow.cbp.base.application.dto.UpdateInput;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrganizationUnitUserUpdateInput extends UpdateInput {
    private static final long serialVersionUID = -433170271932243951L;

    @NotEmpty
    private String userId;

    @NotEmpty
    private String organizationUnitId;
}
