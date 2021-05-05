package com.canknow.cbp.base.settings;

import java.util.List;

public interface ISettingStore {
    SettingInfo getSettingOrNull(String name);

    SettingInfo getSettingOrNull(String name, String userId);

    void delete(SettingInfo settingInfo);

    void create(SettingInfo settingInfo);

    void update(SettingInfo settingInfo);

    List<SettingInfo> getAllList();

    List<SettingInfo> getAllList(String userId);
}
