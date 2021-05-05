package com.canknow.cbp.common.adapter.outbound.persistent.loginAttempt;

import com.canknow.cbp.base.domain.repositories.IRepository;
import com.canknow.cbp.common.domain.authorization.CbpLoginAttempt;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import com.canknow.cbp.jpa.repositories.JpaRepositoryBase;

public abstract class CbpLoginAttemptRepository<TUser extends CbpUser<TUser, TRole>, TRole extends CbpRole<TUser, TRole>, TLoginAttempt extends CbpLoginAttempt<TUser, TRole>>
        extends JpaRepositoryBase<TLoginAttempt, ICbpLoginAttemptJpaRepository<TUser, TRole, TLoginAttempt>> implements IRepository<TLoginAttempt> {
    public CbpLoginAttemptRepository(ICbpLoginAttemptJpaRepository<TUser, TRole, TLoginAttempt> jpaRepository) {
        super(jpaRepository);
    }
}
