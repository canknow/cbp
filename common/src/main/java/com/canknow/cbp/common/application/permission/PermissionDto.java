package com.canknow.cbp.common.application.permission;

import com.canknow.cbp.base.autoMapper.AutoMap;
import lombok.Data;
import java.util.List;

@Data
public class PermissionDto {
    private String name;

    private boolean isGrantedByDefault;

    @AutoMap(type = PermissionDto.class)
    private List<PermissionDto> children;
}
