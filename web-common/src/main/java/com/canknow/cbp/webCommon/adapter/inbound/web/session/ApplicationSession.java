package com.canknow.cbp.webCommon.adapter.inbound.web.session;

import com.canknow.cbp.webCommon.adapter.inbound.web.security.UserPrinciple;
import com.canknow.cbp.base.runtime.session.IApplicationSession;
import org.apache.shiro.SecurityUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component("applicationSession")
@Primary
public class ApplicationSession implements IApplicationSession {
    public UserPrinciple getUserPrinciple () {
        try {
            return (UserPrinciple) SecurityUtils.getSubject().getPrincipal();
        }
        catch (Exception e){
            return null;
        }
    }

    @Override
    public String getUserId () {
        UserPrinciple userPrinciple = getUserPrinciple();
        if (userPrinciple == null) {
            return null;
        }
        return userPrinciple.getId();
    }

    @Override
    public String getUserName() {
        UserPrinciple userPrinciple = getUserPrinciple();

        if (userPrinciple == null) {
            return null;
        }
        return userPrinciple.getUserName();
    }

    @Override
    public String getImpersonatorUserId() {
        return null;
    }
}
