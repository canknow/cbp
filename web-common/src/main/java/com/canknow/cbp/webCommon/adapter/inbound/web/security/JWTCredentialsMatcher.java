package com.canknow.cbp.webCommon.adapter.inbound.web.security;

import com.canknow.cbp.base.security.JWTUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

public class JWTCredentialsMatcher implements CredentialsMatcher {
    /**
     * Matcher中直接调用工具包中的verify方法即可
     */
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        String token = (String) authenticationToken.getCredentials();
        UserPrinciple userPrinciple = (UserPrinciple)(authenticationInfo.getPrincipals().asList().get(0));

        return JWTUtils.verify(token, userPrinciple.getId());
    }
}
