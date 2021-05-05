package com.canknow.cbp.webCommon.adapter.inbound.web.security;

import com.canknow.cbp.base.localization.ILocalizationManager;
import com.canknow.cbp.base.security.JWTUtils;
import com.canknow.cbp.common.domain.permission.CbpPermissionManager;
import com.canknow.cbp.common.domain.user.CbpUser;
import com.canknow.cbp.common.domain.user.IUserRepository;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class JWTAuthorizingRealm extends AuthorizingRealm {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private CbpPermissionManager permissionManager;

    @Autowired
    private ILocalizationManager localizationManager;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    @Override
    public boolean isPermitted(PrincipalCollection principals, String permission){
        UserPrinciple user = (UserPrinciple)principals.getPrimaryPrincipal();
        return user.isAdmin() || super.isPermitted(principals, permission);
    }

    @Override
    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
        UserPrinciple user = (UserPrinciple)principals.getPrimaryPrincipal();
        return user.isAdmin() || super.hasRole(principals,roleIdentifier);
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        JWTToken jwtToken = (JWTToken) authenticationToken;

        String token = (String) jwtToken.getPrincipal();
        // 解密获得userName，用于和数据库进行对比
        String userId = JWTUtils.getUserIdFromToken(token);

        if (userId == null) {
            throw new AuthenticationException(localizationManager.getLocalizationText("userIsNotExit"));
        }
        CbpUser user = (CbpUser) userRepository.get(userId);

        if (JWTUtils.verify(token, userId)) {
            UserPrinciple userPrinciple = new UserPrinciple();
            userPrinciple.setId(user.getId());
            userPrinciple.setUserName(user.getUserName());
            return new SimpleAuthenticationInfo(userPrinciple, user.getPassword(), "jwtRealm");
        }
        throw new AuthenticationException(localizationManager.getLocalizationText("tokenExpiredOrIncorrect"));
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Set<String> permissionList = permissionManager.getCurrentUserPermissions();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addStringPermissions(permissionList);
        return simpleAuthorizationInfo;
    }
}
