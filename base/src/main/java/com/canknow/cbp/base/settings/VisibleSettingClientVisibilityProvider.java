package com.canknow.cbp.base.settings;

import com.canknow.cbp.base.dependency.IIocResolver;

public class VisibleSettingClientVisibilityProvider implements ISettingClientVisibilityProvider {
    @Override
    public boolean checkVisible(IIocResolver iocResolver) {
        return true;
    }
}
