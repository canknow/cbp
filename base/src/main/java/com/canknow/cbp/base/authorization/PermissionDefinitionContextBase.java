package com.canknow.cbp.base.authorization;

import com.canknow.cbp.base.exceptions.ApplicationException;
import org.springframework.beans.factory.annotation.Autowired;

public class PermissionDefinitionContextBase implements IPermissionDefinitionContext {
    @Autowired
    protected PermissionDictionary permissionDictionary;

    @Override
    public Permission getPermissionOrNull(String name) {
        return permissionDictionary.getOrDefault(name, null);
    }

    @Override
    public void removePermission(String name) {
        permissionDictionary.remove(name);
    }

    @Override
    public Permission createPermission(String name) throws ApplicationException {
        if (permissionDictionary.containsKey(name)) {
            throw new ApplicationException("There is already a permission with name: " + name);
        }
        Permission permission = new Permission(name);
        permissionDictionary.registerPermission(permission);
        return permission;
    }
}
