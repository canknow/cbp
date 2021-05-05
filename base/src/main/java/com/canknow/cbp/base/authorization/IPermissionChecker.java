package com.canknow.cbp.base.authorization;

import com.canknow.cbp.base.exceptions.ApplicationException;
import com.canknow.cbp.base.identifier.IUserIdentifier;

public interface IPermissionChecker {
    boolean isGranted(String permissionName) throws ApplicationException;

    boolean isGranted(IUserIdentifier userIdentifier, String permissionName);
}
