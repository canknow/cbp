package com.canknow.cbp.common.domain.organizationUnit;

import com.canknow.cbp.base.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrganizationUnitType implements BaseEnum {
    ORGANIZATION(0, "organization", "organization"),
    DEPARTMENT(1, "department", "department"),
    POST(2, "post", "post");

    private final Integer code;

    private final String name;

    private final String description;
}