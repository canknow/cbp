package com.canknow.cbp.common.domain.session;

import com.canknow.cbp.base.runtime.session.IApplicationSession;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.IUserRepository;
import com.canknow.cbp.common.domain.user.CbpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionHelper<TUser extends CbpUser<TUser, TRole>, TRole extends CbpRole<TUser, TRole>> {
    @Autowired
    private IUserRepository<TUser, TRole> userRepository;

    @Autowired(required = false)
    private IApplicationSession applicationSession;

    public TUser getUser() {
        if (applicationSession.getUserId() == null) {
            return null;
        }
        return userRepository.get(applicationSession.getUserId());
    }
}
