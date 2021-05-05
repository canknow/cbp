package com.canknow.cbp.common.adapter.outbound.persistent.organizationUnitUser;

import com.canknow.cbp.common.domain.organizationUnit.CbpOrganizationUnit;
import com.canknow.cbp.common.domain.organizationUnitUser.CbpOrganizationUnitUser;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import com.canknow.cbp.jpa.repositories.IJpaRepositoryBase;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ICbpOrganizationUnitUserJpaRepository<
        TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>,
        TOrganizationUnit extends CbpOrganizationUnit<TUser, TRole, TOrganizationUnit>,
        TOrganizationUnitUser extends CbpOrganizationUnitUser<TUser, TRole, TOrganizationUnit>>
        extends IJpaRepositoryBase<TOrganizationUnitUser> {
    List<TOrganizationUnitUser> findAllByUserId(String userId);
}
