package com.canknow.cbp.common.adapter.outbound.persistent.role;

import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import com.canknow.cbp.jpa.repositories.IJpaRepositoryBase;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface ICbpRoleJpaRepository<TUser extends CbpUser<TUser, TRole>, TRole extends CbpRole<TUser, TRole>> extends IJpaRepositoryBase<TRole> {
    List<TRole> findAllByIsDefault(Boolean isDefault);

    TRole getByIsDefault(Boolean isDefault);

    TRole getByName(String name);
}
