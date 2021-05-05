package com.canknow.cbp.common.domain.authorization;

import javax.security.sasl.AuthenticationException;

public interface IOpenAuthenticationProvider {
    LoginResult loginByCode(String code) throws AuthenticationException;
}
