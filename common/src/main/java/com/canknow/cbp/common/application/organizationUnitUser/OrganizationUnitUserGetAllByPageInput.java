package com.canknow.cbp.common.application.organizationUnitUser;

import com.canknow.cbp.base.application.dto.GetAllByPageInput;
import lombok.Data;

@Data
public class OrganizationUnitUserGetAllByPageInput extends GetAllByPageInput {
    private String userId;

    private String organizationUnitId;
}
