package com.canknow.cbp.common.adapter.outbound.persistent.organizationUnit;

import com.canknow.cbp.common.domain.organizationUnit.IOrganizationUnitRepository;
import com.canknow.cbp.common.domain.organizationUnit.CbpOrganizationUnit;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import com.canknow.cbp.jpa.repositories.JpaRepositoryBase;

public abstract class CbpOrganizationUnitRepository<
        TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>,
        TOrganizationUnit extends CbpOrganizationUnit<TUser, TRole, TOrganizationUnit>> extends
        JpaRepositoryBase<TOrganizationUnit, ICbpOrganizationUnitJpaRepository<TUser, TRole, TOrganizationUnit>> implements IOrganizationUnitRepository<TUser, TRole, TOrganizationUnit> {

    public CbpOrganizationUnitRepository(ICbpOrganizationUnitJpaRepository<TUser, TRole, TOrganizationUnit> jpaRepository) {
        super(jpaRepository);
    }
}
