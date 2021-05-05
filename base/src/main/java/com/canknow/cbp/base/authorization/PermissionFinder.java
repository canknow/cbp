package com.canknow.cbp.base.authorization;

import com.canknow.cbp.base.exceptions.ApplicationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PermissionFinder {
    public List<Permission> registerAllPermissions(PermissionProvider[] authorizationProviders) throws ApplicationException {
        return new InternalPermissionFinder(authorizationProviders).getAllPermissions();
    }

    public class InternalPermissionFinder extends PermissionDefinitionContextBase {
        public InternalPermissionFinder(PermissionProvider[] authorizationProviders) throws ApplicationException {
            super();

            for (PermissionProvider provider: authorizationProviders){
                provider.registerPermissions(this);
            }
            permissionDictionary.addAllPermissions();
        }

        public List<Permission> getAllPermissions() {
            return new ArrayList<>(permissionDictionary.values());
        }
    }
}
