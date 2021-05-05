package com.canknow.cbp.base.settings;

import com.canknow.cbp.base.dependency.IIocResolver;
import com.canknow.cbp.base.runtime.session.IApplicationSession;

public class RequiresAuthenticationSettingClientVisibilityProvider implements ISettingClientVisibilityProvider{
    @Override
    public boolean checkVisible(IIocResolver iocResolver) {
        return iocResolver.resolve(IApplicationSession.class).getUserId() != null;
    }
}
