package com.canknow.cbp.base.settings;

import java.util.List;

public interface ISettingManager {
    String getSettingValue(String name);

    String getSettingValueForUser(String name, String userId);

    List<SettingValue> getAllSettingValuesForUser(String userId);

    List<SettingValue> getAllSettingValues();

    void changeSetting(String name, String value);

    void changeSettingForApplication(String name, String value);

    void changeSettingForUser(String userId, String name, String value);
}
