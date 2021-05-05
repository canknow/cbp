package com.canknow.cbp.common.application.role;

import com.canknow.cbp.base.application.dto.CreateInput;
import lombok.Data;
import javax.persistence.ElementCollection;
import java.util.HashSet;
import java.util.Set;

@Data
public class RoleCreateInput extends CreateInput {
    private String name;

    private String displayName;

    private String description;

    private boolean isDefault;

    @ElementCollection
    private Set<String> permissions = new HashSet<>();
}
