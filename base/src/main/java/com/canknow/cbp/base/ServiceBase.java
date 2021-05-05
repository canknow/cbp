package com.canknow.cbp.base;

import com.canknow.cbp.base.dependency.IIocResolver;
import com.canknow.cbp.base.localization.ILocalizationManager;
import com.canknow.cbp.base.settings.ISettingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import java.lang.reflect.ParameterizedType;

public abstract class ServiceBase {
    @Autowired
    protected ISettingManager settingManager;

    @Autowired
    protected ILocalizationManager localizationManager;

    @Autowired
    protected IIocResolver iocResolver;

    protected String getLocalizationText (String code) {
        return localizationManager.getLocalizationText(code);
    }

    protected String getLocalizationText (String code, @Nullable Object[] args) {
        return localizationManager.getLocalizationText(code, args);
    }

    protected <T> Class<T> getTClass(int index) {
        return (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[index];
    }
}
