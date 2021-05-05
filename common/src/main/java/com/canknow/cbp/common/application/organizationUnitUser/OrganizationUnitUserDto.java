package com.canknow.cbp.common.application.organizationUnitUser;

import com.canknow.cbp.base.application.dto.Dto;
import lombok.Data;

@Data
public class OrganizationUnitUserDto extends Dto {
    private static final long serialVersionUID = 4544790675639257233L;

    private String userId;

    private String organizationUnitId;
}
