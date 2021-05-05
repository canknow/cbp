package com.canknow.cbp.common.domain.permission;

import com.canknow.cbp.common.domain.user.CbpUserManager;
import com.canknow.cbp.base.exceptions.ApplicationException;
import com.canknow.cbp.base.authorization.IPermissionChecker;
import com.canknow.cbp.base.runtime.session.IApplicationSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import com.canknow.cbp.base.identifier.IUserIdentifier;

@Component("defaultPermissionChecker")
@Primary
public class PermissionChecker implements IPermissionChecker {
    @Autowired(required = false)
    public IApplicationSession applicationSession;

    @Autowired
    public CbpUserManager userManager;

    @Override
    public boolean isGranted(String permissionName) throws ApplicationException {
        if (applicationSession.getUserId() != null) {
            return userManager.isGranted(applicationSession.getUserId(), permissionName);
        }
        return false;
    }

    @Override
    public boolean isGranted(IUserIdentifier userIdentifier, String permissionName) {
        return false;
    }
}
