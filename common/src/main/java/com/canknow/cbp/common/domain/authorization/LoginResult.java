package com.canknow.cbp.common.domain.authorization;

import com.canknow.cbp.common.domain.user.CbpUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResult {
    private LoginResultType loginResultType;

    private boolean useVerifyCode = false;

    private CbpUser user;

    public LoginResult(LoginResultType loginResultType) {
        this.loginResultType = loginResultType;
    }

    public LoginResult(LoginResultType loginResultType, CbpUser user) {
        this.loginResultType = loginResultType;
        this.user = user;
    }

    public static LoginResult success(CbpUser user) {
        LoginResult loginResult = new LoginResult();
        loginResult.setUser(user);
        loginResult.setLoginResultType(LoginResultType.SUCCESS);
        return loginResult;
    }
}
