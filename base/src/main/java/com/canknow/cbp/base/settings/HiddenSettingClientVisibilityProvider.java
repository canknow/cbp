package com.canknow.cbp.base.settings;

import com.canknow.cbp.base.dependency.IIocResolver;

public class HiddenSettingClientVisibilityProvider implements ISettingClientVisibilityProvider {
    @Override
    public boolean checkVisible(IIocResolver iocResolver) {
        return false;
    }
}
