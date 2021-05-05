package com.canknow.cbp.common.adapter.outbound.persistent.organizationUnit;

import com.canknow.cbp.common.domain.organizationUnit.CbpOrganizationUnit;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import com.canknow.cbp.jpa.repositories.IJpaRepositoryBase;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ICbpOrganizationUnitJpaRepository<
        TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>,
        TOrganizationUnit extends CbpOrganizationUnit<TUser, TRole, TOrganizationUnit>>
        extends IJpaRepositoryBase<TOrganizationUnit> {
}
