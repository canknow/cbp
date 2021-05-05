package com.canknow.cbp.base.authorization;

import com.canknow.cbp.base.exceptions.ApplicationException;
import org.springframework.stereotype.Component;

@Component("emptyPermissionProvider")
public class EmptyPermissionProvider extends PermissionProvider {
    @Override
    public void registerPermissions(IPermissionDefinitionContext context) throws ApplicationException {

    }
}
