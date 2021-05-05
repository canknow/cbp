package com.canknow.cbp.webCommon.adapter.inbound.web.security;

import com.canknow.cbp.common.domain.user.CbpUser;
import lombok.Data;

@Data
public class UserPrinciple {
    private String id;

    private String userName;

    public boolean isAdmin() {
        return userName.equals(CbpUser.ADMIN_USER_NAME);
    }
}
