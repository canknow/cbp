package com.canknow.cbp.common.application.role;

import com.canknow.cbp.base.application.dto.FullAuditedDto;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
public class RoleDto extends FullAuditedDto {
    private static final long serialVersionUID = 5378425716166580189L;
    private String name;

    private String displayName;

    private String description;

    private boolean isStatic;

    private boolean isDefault;

    private Set<String> permissions = new HashSet();
}
