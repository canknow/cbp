package com.canknow.cbp.common.domain.authorization;

import com.canknow.cbp.base.domain.repositories.IRepository;

public interface IUserLoginRepository extends IRepository<UserLogin> {
    UserLogin findByProviderKey(String providerKey);
}
