package com.canknow.cbp.common.adapter.outbound.persistent.authorization;

import com.canknow.cbp.jpa.repositories.JpaRepositoryBase;
import com.canknow.cbp.common.domain.authorization.IUserLoginRepository;
import com.canknow.cbp.common.domain.authorization.UserLogin;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class UserLoginRepository extends JpaRepositoryBase<UserLogin, IUserLoginJpaRepository>
        implements IUserLoginRepository {

    public UserLoginRepository(IUserLoginJpaRepository jpaRepository) {
        super(jpaRepository);
    }


    public UserLogin findByProviderKey(String providerKey) {
        return jpaRepository.findByProviderKey(providerKey);
    }
}
