package com.canknow.cbp.common.application.user;

import com.canknow.cbp.base.application.dto.Dto;
import lombok.Data;

@Data
public class RoleDto extends Dto {
    private String name;

    private String displayName;
}
