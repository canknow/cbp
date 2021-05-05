package com.canknow.cbp.common.adapter.outbound.persistent.user;

import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import com.canknow.cbp.common.domain.user.IUserRepository;
import com.canknow.cbp.jpa.repositories.FullAuditedJpaRepositoryBase;

import java.util.List;

public abstract class CbpUserRepository<TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>> extends FullAuditedJpaRepositoryBase<TUser, ICbpUserJpaRepository<TUser, TRole>> implements IUserRepository<TUser, TRole> {
    public CbpUserRepository(ICbpUserJpaRepository<TUser, TRole> jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public TUser getByUserName(String userName) {
        return jpaRepository.getByUserName(userName);
    }

    @Override
    public List<TUser> findAllByIdInAndPhoneNumberNotNull(List<String> ids) {
        return jpaRepository.findAllByIdInAndPhoneNumberNotNull(ids);
    }
}
