package com.canknow.cbp.common.domain.user;

import com.canknow.cbp.base.domain.domainServices.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInvitationService extends DomainService {
    @Autowired
    protected IUserInvitationRepository userInvitationRepository;

    public UserInvitation invite(CbpUser user) {
        UserInvitation userInvitation = userInvitationRepository.findByUserId(user.getId());

        if (userInvitation==null) {
            userInvitation = new UserInvitation();
            userInvitation.setUserId(user.getId());
            userInvitationRepository.insert(userInvitation);
        }
        return userInvitation;
    }

    public void join(UserInvitation userInvitation, CbpUser user) {

    }
}
