package com.canknow.cbp.common.application.loginAttempt;

import com.canknow.cbp.base.application.applicationService.GetApplicationService;
import com.canknow.cbp.base.application.dto.FindInput;
import com.canknow.cbp.base.application.dto.GetAllInput;
import com.canknow.cbp.common.application.loginAttempt.LoginAttemptDto;
import com.canknow.cbp.common.application.loginAttempt.LoginAttemptGetAllInput;
import com.canknow.cbp.common.domain.authorization.CbpLoginAttempt;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;

public abstract class LoginAttemptApplicationService<TUser extends CbpUser<TUser, TRole>,
        TRole extends CbpRole<TUser, TRole>,
        TLoginAttempt extends CbpLoginAttempt<TUser, TRole>> extends GetApplicationService<TLoginAttempt, LoginAttemptDto, LoginAttemptDto, LoginAttemptDto, FindInput, GetAllInput, LoginAttemptGetAllInput> {
}
