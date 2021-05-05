package com.canknow.cbp.common.domain.user;

import com.canknow.cbp.common.domain.authorization.UserLogin;

public interface IUserNumberProvider {
    String generateNumber(CbpUser user, UserLogin userLogin);
}
