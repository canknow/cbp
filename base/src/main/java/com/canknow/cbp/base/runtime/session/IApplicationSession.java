package com.canknow.cbp.base.runtime.session;

public interface IApplicationSession {
    String getUserId();

    String getUserName();

    String getImpersonatorUserId();
}
