package com.canknow.cbp.base.runtime.session;

import org.springframework.stereotype.Component;

@Component("nullApplicationSession")
public class NullApplicationSession implements IApplicationSession {
    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getImpersonatorUserId() {
        return null;
    }
}
