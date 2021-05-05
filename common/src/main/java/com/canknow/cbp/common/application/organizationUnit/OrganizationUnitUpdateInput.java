package com.canknow.cbp.common.application.organizationUnit;

import com.canknow.cbp.base.application.dto.UpdateInput;
import com.canknow.cbp.common.domain.organizationUnit.OrganizationUnitType;
import lombok.Data;

@Data
public class OrganizationUnitUpdateInput extends UpdateInput {
    private String parentId;

    private String name;

    private String description;

    private OrganizationUnitType type;

    private String provinceRegionId;

    private String cityRegionId;

    private String countyRegionId;

    private String regionId;
}
