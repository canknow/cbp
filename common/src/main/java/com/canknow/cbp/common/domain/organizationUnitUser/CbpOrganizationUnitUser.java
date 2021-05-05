package com.canknow.cbp.common.domain.organizationUnitUser;

import com.canknow.cbp.base.domain.entities.audit.CreationAuditedAggregateRoot;
import com.canknow.cbp.common.domain.organizationUnit.CbpOrganizationUnit;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class CbpOrganizationUnitUser<
        TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>,
        TOrganizationUnit extends CbpOrganizationUnit<TUser, TRole, TOrganizationUnit>>
        extends CreationAuditedAggregateRoot {
    private static final long serialVersionUID = 3119151827223023911L;

    private String userId;

    private String organizationUnitId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private TUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizationUnitId", insertable = false, updatable = false)
    private TOrganizationUnit organizationUnit;
}
