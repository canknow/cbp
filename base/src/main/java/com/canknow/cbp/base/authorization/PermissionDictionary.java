package com.canknow.cbp.base.authorization;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class PermissionDictionary extends HashMap<String, Permission> {
    private static final long serialVersionUID = 273704429743473321L;
    private final List<Permission> permissions = new ArrayList<>();

    public void registerPermission(Permission permission) {
        this.permissions.add(permission);
    }

    public List<Permission> getPermissionTree() {
        return this.permissions;
    }

    public void addAllPermissions() throws ApplicationInitializationException {
        for (Permission permission: permissions) {
            addPermissionRecursively(permission);
        }
    }

    private void addPermissionRecursively(Permission permission) throws ApplicationInitializationException {
        Permission existingPermission = get(permission.getName());

        if (existingPermission == null || !existingPermission.equals(permission)) {
            put(permission.getName(), permission);
        }
        else {
            throw new ApplicationInitializationException("Duplicate permission name detected for " + permission.getName());
        }

        for (Permission childPermission: permission.getChildren()) {
            addPermissionRecursively(childPermission);
        }
    }
}
