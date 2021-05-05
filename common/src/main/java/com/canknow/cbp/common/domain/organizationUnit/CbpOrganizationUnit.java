package com.canknow.cbp.common.domain.organizationUnit;

import com.canknow.cbp.base.domain.entities.audit.FullAuditedAggregateRoot;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@MappedSuperclass
public abstract class CbpOrganizationUnit<TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>,
        TOrganizationUnit extends CbpOrganizationUnit<TUser, TRole, TOrganizationUnit>> extends FullAuditedAggregateRoot {
    @Column(length = 64)
    private String parentId;

    @ManyToOne
    @JoinColumn(name = "parentId", insertable = false, updatable = false, nullable = true)
    private TOrganizationUnit parent;

    @OneToMany(cascade=CascadeType.MERGE, fetch=FetchType.LAZY, mappedBy = "parent")
    private List<TOrganizationUnit> children = new ArrayList<>();

    @Column(nullable = false, length = 20)
    private String name;

    private String description;

    @Enumerated
    private OrganizationUnitType type;

    @Column(length = 64)
    private String provinceRegionId;

    @Column(length = 64)
    private String cityRegionId;

    @Column(length = 64)
    private String countyRegionId;

    @Column(length = 64)
    private String regionId;
}
