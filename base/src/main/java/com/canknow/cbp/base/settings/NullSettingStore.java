package com.canknow.cbp.base.settings;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("nullSettingStore")
public class NullSettingStore implements ISettingStore {
    @Override
    public SettingInfo getSettingOrNull(String name) {
        return null;
    }

    @Override
    public SettingInfo getSettingOrNull(String name, String userId) {
        return null;
    }

    @Override
    public void delete(SettingInfo settingInfo) {

    }

    @Override
    public void create(SettingInfo settingInfo) {

    }

    @Override
    public void update(SettingInfo settingInfo) {

    }

    @Override
    public List<SettingInfo> getAllList() {
        return null;
    }

    @Override
    public List<SettingInfo> getAllList(String userId) {
        return null;
    }
}
