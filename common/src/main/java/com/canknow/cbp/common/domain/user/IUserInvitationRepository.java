package com.canknow.cbp.common.domain.user;

import com.canknow.cbp.base.domain.repositories.IRepository;

public interface IUserInvitationRepository extends IRepository<UserInvitation> {
    UserInvitation findByUserId(String userId);
}
