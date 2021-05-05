package com.canknow.cbp.common.application.role;

import com.canknow.cbp.base.application.dto.UpdateInput;
import lombok.Data;
import javax.persistence.ElementCollection;
import java.util.HashSet;
import java.util.Set;

@Data
public class RoleUpdateInput extends UpdateInput {
    private static final long serialVersionUID = 2051545358905904828L;
    private String name;

    private String displayName;

    private String description;

    private boolean isDefault;

    @ElementCollection
    private Set<String> permissions = new HashSet<>();
}
