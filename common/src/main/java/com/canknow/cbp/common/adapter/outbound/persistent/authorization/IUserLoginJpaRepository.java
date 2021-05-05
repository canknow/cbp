package com.canknow.cbp.common.adapter.outbound.persistent.authorization;

import com.canknow.cbp.jpa.repositories.IJpaRepositoryBase;
import com.canknow.cbp.common.domain.authorization.UserLogin;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserLoginJpaRepository extends IJpaRepositoryBase<UserLogin> {
    UserLogin findByProviderKey(String providerKey);
}
