package com.canknow.cbp.base.authorization;

import com.canknow.cbp.base.exceptions.ApplicationException;

public abstract class PermissionProvider {
    public abstract void registerPermissions(IPermissionDefinitionContext context) throws ApplicationException;
}
