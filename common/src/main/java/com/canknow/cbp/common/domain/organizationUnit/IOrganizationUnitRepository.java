package com.canknow.cbp.common.domain.organizationUnit;

import com.canknow.cbp.base.domain.repositories.IRepository;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;

public interface IOrganizationUnitRepository<TUser extends CbpUser<TUser, TRole>, TRole extends CbpRole<TUser, TRole>, TOrganizationUnit extends CbpOrganizationUnit<TUser, TRole, TOrganizationUnit>>
        extends IRepository<TOrganizationUnit> {
}
