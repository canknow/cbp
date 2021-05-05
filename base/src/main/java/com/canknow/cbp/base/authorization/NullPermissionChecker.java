package com.canknow.cbp.base.authorization;

import com.canknow.cbp.base.exceptions.ApplicationException;
import com.canknow.cbp.base.identifier.IUserIdentifier;
import org.springframework.stereotype.Component;

@Component("nullPermissionChecker")
public class NullPermissionChecker implements IPermissionChecker {
    @Override
    public boolean isGranted(String permissionName) throws ApplicationException {
        return false;
    }

    @Override
    public boolean isGranted(IUserIdentifier userIdentifier, String permissionName) {
        return false;
    }
}
