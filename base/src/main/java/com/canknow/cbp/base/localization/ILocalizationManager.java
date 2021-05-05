package com.canknow.cbp.base.localization;

public interface ILocalizationManager {
    String getLocalizationText(String name);

    String getLocalizationText(String name, Object[] args);
}
