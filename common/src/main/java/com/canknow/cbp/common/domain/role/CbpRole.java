package com.canknow.cbp.common.domain.role;

import com.canknow.cbp.base.domain.entities.audit.FullAuditedAggregateRoot;
import com.canknow.cbp.common.domain.user.CbpUser;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class CbpRole<TUser extends CbpUser<TUser, TRole>, TRole extends CbpRole<TUser, TRole>> extends FullAuditedAggregateRoot {
    private static final long serialVersionUID = -581288355856520649L;
    private String name;

    private String displayName;

    private String description;

    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    private boolean isDefault;

    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    private boolean isStatic;

    private String parentId;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<TUser> users = new HashSet<>();

    @ElementCollection(fetch = FetchType.LAZY)
    private Set<String> permissions = new HashSet<>();
}
