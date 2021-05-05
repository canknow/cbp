package com.canknow.cbp.common.adapter.outbound.persistent.settings;

import com.canknow.cbp.base.settings.ISettingStore;
import com.canknow.cbp.base.settings.SettingInfo;
import com.canknow.cbp.common.domain.settings.ISettingRepository;
import com.canknow.cbp.common.domain.settings.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("defaultSettingStore")
@Primary
public class SettingStore implements ISettingStore {
    @Autowired
    private ISettingRepository settingRepository;

    @Override
    public SettingInfo getSettingOrNull(String name) {
        return getSettingOrNull(name, null);
    }

    @Override
    public SettingInfo getSettingOrNull(String name, String userId) {
        Setting setting = settingRepository.findByNameAndUserId(name, userId);

        if (setting == null) {
            return null;
        }
        return setting.toSettingInfo();
    }

    @Override
    public void delete(SettingInfo settingInfo) {
        Setting setting = settingRepository.findByName(settingInfo.getName());
        settingRepository.delete(setting);

        this.clearGetAllListCache();
    }

    @Override
    public void create(SettingInfo settingInfo) {
        Setting setting = new Setting();
        setting.setName(settingInfo.getName());
        setting.setValue(settingInfo.getValue());
        settingRepository.insert(setting);

        this.clearGetAllListCache();
    }

    @Override
    public void update(SettingInfo settingInfo) {
        Setting setting = settingRepository.findByName(settingInfo.getName());
        setting.setValue(settingInfo.getValue());
        settingRepository.update(setting);

        this.clearGetAllListCache();
    }

    @Override
    public List<SettingInfo> getAllList() {
        return settingRepository.getAll().stream().map(Setting::toSettingInfo).collect(Collectors.toList());
    }

    @Override
    public List<SettingInfo> getAllList(String userId) {
        return settingRepository.getAllByUserId(userId).stream().map(Setting::toSettingInfo).collect(Collectors.toList());
    }

    public void clearGetAllListCache() {
    }
}
