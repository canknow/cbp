package com.canknow.cbp.common.domain.role;

import com.canknow.cbp.base.domain.repositories.IRepository;
import com.canknow.cbp.common.domain.user.CbpUser;

import java.util.List;

public interface IRoleRepository<TUser extends CbpUser<TUser, TRole>, TRole extends CbpRole<TUser, TRole>> extends IRepository<TRole> {
    List<TRole> findAllOfDefault();

    TRole findDefault();

    TRole findByName(String name);
}
