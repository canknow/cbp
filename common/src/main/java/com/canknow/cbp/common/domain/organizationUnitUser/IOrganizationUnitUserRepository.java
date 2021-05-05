package com.canknow.cbp.common.domain.organizationUnitUser;

import com.canknow.cbp.base.domain.repositories.IRepository;
import com.canknow.cbp.common.domain.organizationUnit.CbpOrganizationUnit;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;

import java.util.List;

public interface IOrganizationUnitUserRepository<
        TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>,
        TOrganizationUnit extends CbpOrganizationUnit<TUser, TRole, TOrganizationUnit>,
        TOrganizationUnitUser extends CbpOrganizationUnitUser<TUser, TRole, TOrganizationUnit>
        >
        extends IRepository<TOrganizationUnitUser> {

    List<TOrganizationUnitUser> findAllByUserId(String userId);
}
