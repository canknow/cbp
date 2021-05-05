package com.canknow.cbp.common.application.organizationUnitUser;

import com.canknow.cbp.base.application.dto.GetAllInput;
import lombok.Data;

@Data
public class OrganizationUnitUserGetAllInput extends GetAllInput {
    private String userId;

    private String organizationUnitId;
}
