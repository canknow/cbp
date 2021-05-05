package com.canknow.cbp.common.application.organizationUnit;

import com.canknow.cbp.base.application.dto.CreateInput;
import com.canknow.cbp.common.domain.organizationUnit.OrganizationUnitType;
import lombok.Data;

@Data
public class OrganizationUnitCreateInput extends CreateInput {
    private String parentId;

    private String name;

    private String description;

    private OrganizationUnitType type;

    private String provinceRegionId;

    private String cityRegionId;

    private String countyRegionId;

    private String regionId;
}
