package com.canknow.cbp.common.adapter.outbound.persistent.organizationUnitUser;

import com.canknow.cbp.common.domain.organizationUnit.CbpOrganizationUnit;
import com.canknow.cbp.common.domain.organizationUnitUser.CbpOrganizationUnitUser;
import com.canknow.cbp.common.domain.organizationUnitUser.IOrganizationUnitUserRepository;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import com.canknow.cbp.jpa.repositories.JpaRepositoryBase;

import java.util.List;

public abstract class CbpOrganizationUnitUserRepository<
        TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>,
        TOrganizationUnit extends CbpOrganizationUnit<TUser, TRole, TOrganizationUnit>,
        TOrganizationUnitUser extends CbpOrganizationUnitUser<TUser, TRole, TOrganizationUnit>> extends
        JpaRepositoryBase<TOrganizationUnitUser, ICbpOrganizationUnitUserJpaRepository<TUser, TRole, TOrganizationUnit, TOrganizationUnitUser>>
        implements IOrganizationUnitUserRepository<TUser, TRole, TOrganizationUnit, TOrganizationUnitUser> {

    public CbpOrganizationUnitUserRepository(ICbpOrganizationUnitUserJpaRepository<TUser, TRole, TOrganizationUnit, TOrganizationUnitUser> jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public List<TOrganizationUnitUser> findAllByUserId(String userId) {
        return jpaRepository.findAllByUserId(userId);
    }
}
