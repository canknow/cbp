package com.canknow.cbp.common.adapter.outbound.persistent.user;

import com.canknow.cbp.jpa.repositories.JpaRepositoryBase;
import com.canknow.cbp.common.domain.user.IUserInvitationRepository;
import com.canknow.cbp.common.domain.user.UserInvitation;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class UserInvitationRepository extends JpaRepositoryBase<UserInvitation, IUserInvitationJpaRepository> implements IUserInvitationRepository {
    public UserInvitationRepository(IUserInvitationJpaRepository jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public UserInvitation findByUserId(String userId) {
        return null;
    }
}
