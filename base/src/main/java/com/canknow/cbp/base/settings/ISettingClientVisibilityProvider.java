package com.canknow.cbp.base.settings;

import com.canknow.cbp.base.dependency.IIocResolver;

public interface ISettingClientVisibilityProvider {
    boolean checkVisible(IIocResolver iocResolver);
}
