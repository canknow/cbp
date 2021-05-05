package com.canknow.cbp.common.domain.user;

import com.canknow.cbp.common.domain.authorization.UserLogin;
import com.canknow.cbp.utils.DateUtils;
import org.springframework.stereotype.Component;

@Component
public class UserNumberProvider implements IUserNumberProvider {
    @Override
    public String generateNumber(CbpUser user, UserLogin userLogin) {
        String date = DateUtils.format("yyyyMMdd");
        return date + new java.util.Random().nextInt(900) + 100;
    }
}
