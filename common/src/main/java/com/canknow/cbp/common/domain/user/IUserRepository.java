package com.canknow.cbp.common.domain.user;

import com.canknow.cbp.base.domain.repositories.IRepository;
import com.canknow.cbp.common.domain.role.CbpRole;

import java.util.List;

public interface IUserRepository<
        TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>> extends IRepository<TUser> {
    TUser getByUserName(String userName);

    List<TUser> findAllByIdInAndPhoneNumberNotNull(List<String> ids);
}
