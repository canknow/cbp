package com.canknow.cbp.common.adapter.outbound.persistent.loginAttempt;

import com.canknow.cbp.common.domain.authorization.CbpLoginAttempt;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import com.canknow.cbp.jpa.repositories.IJpaRepositoryBase;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ICbpLoginAttemptJpaRepository<TUser extends CbpUser<TUser, TRole>, TRole extends CbpRole<TUser, TRole>, TLoginAttempt extends CbpLoginAttempt<TUser, TRole>> extends IJpaRepositoryBase<TLoginAttempt> {
}
