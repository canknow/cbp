package com.canknow.cbp.common.domain.authorization;

import com.canknow.cbp.base.clientInfoProvider.IClientInfoProvider;
import com.canknow.cbp.base.domain.domainServices.DomainService;
import com.canknow.cbp.base.domain.repositories.IRepository;
import com.canknow.cbp.base.runtime.session.ISessionService;
import com.canknow.cbp.base.security.IStringCipher;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import com.canknow.cbp.common.domain.user.IUserRepository;
import com.canknow.cbp.utils.IpUtil;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CbpLoginManager<
        TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>,
        TLoginAttempt extends CbpLoginAttempt<TUser, TRole>,
        TUserRepository extends IUserRepository<TUser, TRole>
        > extends DomainService {
    @Autowired
    public IClientInfoProvider clientInfoProvider;

    @Autowired
    public IRepository<TLoginAttempt> loginAttemptRepository;

    @Autowired
    protected TUserRepository userRepository;

    @Autowired
    protected IStringCipher stringCipher;

    @Autowired
    protected ISessionService sessionService;

    public LoginResult login(String userName, String password) throws InstantiationException, IllegalAccessException {
        TUser user = userRepository.getByUserName(userName);

        if (user==null) {
            return buildFailedLoginResult(userName, LoginResultType.INVALID_USER_NAME);
        }

        String realPassword = user.getPassword();
        Object salt = ByteSource.Util.bytes(userName);

        if (!realPassword.equals(stringCipher.encrypt(password, salt))) {
            return buildFailedLoginResult(userName, LoginResultType.INVALID_PASSWORD);
        }
        LoginResult loginResult = createLoginResultAsync(user);
        saveLoginAttempt(loginResult);
        return loginResult;
    }

    protected LoginResult buildFailedLoginResult(String userName, LoginResultType loginResultType) {
        String sessionKey = "session.login.error." + userName;
        Object loginError = sessionService.get(sessionKey);

        long loginErrorCount = 0;

        if (null != loginError) {
            loginErrorCount = Long.parseLong(loginError.toString());
        }
        loginErrorCount += 1;
        sessionService.put(sessionKey, loginErrorCount + "");
        LoginResult loginResult = new LoginResult(LoginResultType.INVALID_USER_NAME);
        loginResult.setUseVerifyCode(true);
        return loginResult;
    }

    protected LoginResult createLoginResultAsync(TUser user) {
        if (!user.isActive()) {
            return new LoginResult(LoginResultType.USER_IS_NOT_ACTIVE, user);
        }
        if (user.isLocked()) {
            return new LoginResult(LoginResultType.LOCKED_OUT, user);
        }
        return new LoginResult(LoginResultType.SUCCESS, user);
    }

    public void saveLoginAttempt(LoginResult loginResult) throws IllegalAccessException, InstantiationException {
        Class<TLoginAttempt>  tLoginAttemptClass = getTClass(2);
        TLoginAttempt loginAttempt = tLoginAttemptClass.newInstance();
        loginAttempt.setLoginResultType(loginResult.getLoginResultType());
        loginAttempt.setUserId(loginResult.getUser() == null ? null : loginResult.getUser().getId());
        loginAttempt.setBrowserInfo(clientInfoProvider.getBrowserInfo());
        loginAttempt.setClientIpAddress(clientInfoProvider.getClientIpAddress());
        loginAttempt.setClientName(clientInfoProvider.getComputerName());
        loginAttempt.setRegion(IpUtil.getRegion(loginAttempt.getClientIpAddress()));
        loginAttemptRepository.insert(loginAttempt);
    }
}
