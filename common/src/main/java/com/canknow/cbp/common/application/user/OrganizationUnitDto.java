package com.canknow.cbp.common.application.user;

import com.canknow.cbp.base.application.dto.Dto;
import com.canknow.cbp.common.domain.organizationUnit.OrganizationUnitType;
import lombok.Data;

@Data
public class OrganizationUnitDto extends Dto {
    private String parentId;

    private String name;

    private String description;

    private OrganizationUnitType type;

    private String provinceRegionId;

    private String cityRegionId;

    private String countyRegionId;

    private String regionId;
}
