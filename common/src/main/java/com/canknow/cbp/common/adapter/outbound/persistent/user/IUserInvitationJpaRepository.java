package com.canknow.cbp.common.adapter.outbound.persistent.user;

import com.canknow.cbp.jpa.repositories.IJpaRepositoryBase;
import com.canknow.cbp.common.domain.user.UserInvitation;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserInvitationJpaRepository extends IJpaRepositoryBase<UserInvitation> {
}
