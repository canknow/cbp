package com.canknow.cbp.base.authorization;

import com.canknow.cbp.base.exceptions.ApplicationException;
import com.canknow.cbp.base.runtime.session.IApplicationSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PermissionManager extends PermissionDefinitionContextBase {
    @Autowired(required = false)
    public IApplicationSession applicationSession;

    @Autowired
    protected List<PermissionProvider> permissionProviders;

    public void initialize() throws ApplicationException {
        for (PermissionProvider permissionProvider: permissionProviders) {
            permissionProvider.registerPermissions(this);
        }
        permissionDictionary.addAllPermissions();
    }

    public Permission getPermission(String name) throws ApplicationException {
        Permission permission = permissionDictionary.getOrDefault(name, null);

        if (permission == null) {
            throw new ApplicationException("There is no permission with name: " + name);
        }

        return permission;
    }

    public List<Permission> getAllPermissions() {
        return new ArrayList<>(permissionDictionary.values());
    }

    public List<Permission> getPermissionTree() {
        return permissionDictionary.getPermissionTree();
    }
}
