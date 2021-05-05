package com.canknow.cbp.base.backgroundJob;

import com.canknow.cbp.base.localization.ILocalizationManager;
import com.canknow.cbp.base.settings.ISettingManager;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BackgroundJob<TArgs> implements IBackgroundJob<TArgs> {
    @Autowired
    protected ISettingManager settingManager;

    @Autowired
    protected ILocalizationManager localizationManager;
}
