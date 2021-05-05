package com.canknow.cbp.base.authorization;

import com.canknow.cbp.base.exceptions.ApplicationException;

public interface IPermissionDefinitionContext {
    Permission getPermissionOrNull(String name);

    void removePermission(String name);

    Permission createPermission(String name) throws ApplicationException;
}
