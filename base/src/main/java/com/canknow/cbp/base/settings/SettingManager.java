package com.canknow.cbp.base.settings;

import com.canknow.cbp.base.runtime.session.IApplicationSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SettingManager implements ISettingManager {
    @Autowired
    private ISettingDefinitionManager _settingDefinitionManager;

    @Autowired
    private ISettingStore settingStore;

    @Autowired
    private IApplicationSession applicationSession;

    public final static String ApplicationSettingsCacheKey = "ApplicationSettings";

    @Override
    public String getSettingValue(String name) {
        return getSettingValueInternal(name, applicationSession.getUserId(), true);
    }

    @Override
    public String getSettingValueForUser(String name, String userId) {
        return getSettingValueInternal(name, userId, true);
    }

    @Override
    public List<SettingValue> getAllSettingValuesForUser(String userId) {
        return (getReadOnlyUserSettings(userId)).values()
                .stream().map(setting -> new SettingValue(setting.getName(), setting.getValue())).collect(Collectors.toList());
    }

    @Override
    public List<SettingValue> getAllSettingValues() {
        return getAllSettingValues(SettingScopes.All);
    }

    @Override
    public void changeSetting(String name, String value) {
        insertOrUpdateOrDeleteSettingValue(name, value, null);
    }

    @Override
    public void changeSettingForApplication(String name, String value) {
        insertOrUpdateOrDeleteSettingValue(name, value, null);
    }

    @Override
    public void changeSettingForUser(String userId, String name, String value) {
        insertOrUpdateOrDeleteSettingValue(name, value, userId);
        clearUserCache(userId);
    }

    @CacheEvict(ApplicationSettingsCacheKey)
    public void clearUserCache(String userId) {
    }

    private SettingInfo insertOrUpdateOrDeleteSettingValue(String name, String value, String userId) {
        SettingDefinition settingDefinition = _settingDefinitionManager.getSettingDefinition(name);
        SettingInfo settingValue = settingStore.getSettingOrNull(name, userId);

        Object defaultValue = settingDefinition.getDefaultValue();

        //No need to store on database if the value is the default value
        if (value == defaultValue) {
            if (settingValue != null) {
                settingStore.delete(settingValue);
            }
            return null;
        }

        //If it's not default value and not stored on database, then insert it
        if (settingValue == null) {

            settingValue = new SettingInfo(name, value, userId);
            settingStore.create(settingValue);
            return settingValue;
        }

        //It's same value in database, no need to update
        Object rawSettingValue = settingValue.getValue();

        if (rawSettingValue == value) {
            return settingValue;
        }

        //Update the setting on database.
        settingValue.setValue(value);
        settingStore.update(settingValue);

        return settingValue;
    }

    public List<SettingValue> getAllSettingValues(SettingScopes scopes) {
        Map<String, SettingDefinition> settingDefinitions = new HashMap<>();
        Map<String, SettingValue> settingValues = new HashMap<>();

        //Fill all setting with default values.
        _settingDefinitionManager.getAllSettingDefinitions().forEach(settingDefinition -> {
            settingDefinitions.put(settingDefinition.getName(), settingDefinition);
            settingValues.put(settingDefinition.getName(), new SettingValue(settingDefinition.getName(), settingDefinition.getDefaultValue()));
        });

        //Overwrite application settings
        if (scopes.hasFlag(SettingScopes.Application)) {
            for (SettingValue settingValue : getAllSettingValuesForApplication()) {
                SettingDefinition setting = settingDefinitions.getOrDefault(settingValue.getName(), null);

                //TODO: Conditions get complicated, try to simplify it
                if (setting == null || !setting.getScopes().hasFlag(SettingScopes.Application)) {
                    continue;
                }

                if (!setting.isInherited() && ((setting.getScopes().hasFlag(SettingScopes.User) && applicationSession.getUserId() != null))) {
                    continue;
                }
                settingValues.put(settingValue.getName(), new SettingValue(settingValue.getName(), settingValue.getValue()));
            }
        }

        //Overwrite user settings
        if (scopes.hasFlag(SettingScopes.User) && applicationSession.getUserId() != null) {
            for (SettingValue settingValue: getAllSettingValuesForUser(applicationSession.getUserId())) {
                SettingDefinition setting = settingDefinitions.getOrDefault(settingValue.getName(), null);

                if (setting != null && setting.getScopes().hasFlag(SettingScopes.User)) {
                    settingValues.put(settingValue.getName(), new SettingValue(settingValue.getName(), settingValue.getValue()));
                }
            }
        }

        return new ArrayList<>(settingValues.values());
    }

    public List<SettingValue> getAllSettingValuesForApplication() {
        return getApplicationSettingsFromCache().values().stream().map(settingInfo ->
                new SettingValue(settingInfo.getName(), settingInfo.getValue())).collect(Collectors.toList());
    }

    private String getSettingValueInternal(String name, String userId, boolean fallbackToDefault) {
        SettingDefinition settingDefinition = _settingDefinitionManager.getSettingDefinition(name);

        if (settingDefinition.getScopes() == SettingScopes.User && StringUtils.isNoneEmpty(userId)) {
            SettingInfo settingValue = getSettingValueForUserOrNull(userId, name);

            if (settingValue != null) {
                return settingValue.getValue();
            }

            if (!fallbackToDefault) {
                return null;
            }

            if (!settingDefinition.isInherited()) {
                return settingDefinition.getDefaultValue();
            }
        }

        if (settingDefinition.getScopes().hasFlag(SettingScopes.Application)) {
            SettingInfo settingInfo = getSettingValueForApplicationOrNull(name);

            if (settingInfo != null) {
                return settingInfo.getValue();
            }

            if (!fallbackToDefault) {
                return null;
            }
        }

        //Not defined, get default value
        return settingDefinition.getDefaultValue();
    }

    private SettingInfo getSettingValueForApplicationOrNull(String name) {
        Map<String, SettingInfo> settingInfoMap = getApplicationSettingsFromCache();
        return settingInfoMap.getOrDefault(name, null);
    }

    @Cacheable(cacheNames = ApplicationSettingsCacheKey)
    public Map<String, SettingInfo> getApplicationSettingsFromCache() {
        List<SettingInfo> settingValues = settingStore.getAllList();
        return convertSettingInfosToDictionary(settingValues);
    }

    private SettingInfo getSettingValueForUserOrNull(String userId, String name) {
        return (getReadOnlyUserSettings(userId)).getOrDefault(name, null);
    }

    private Map<String, SettingInfo> getReadOnlyUserSettings(String userId) {
        return getUserSettingsFromCache(userId);
    }

    @Cacheable(cacheNames = ApplicationSettingsCacheKey, key = "#userId")
    public Map<String, SettingInfo> getUserSettingsFromCache(String userId) {
        List<SettingInfo> settingInfos = settingStore.getAllList(userId);
        return convertSettingInfosToDictionary(settingInfos);
    }

    private Map<String, SettingInfo> convertSettingInfosToDictionary(List<SettingInfo> settingValues) {
        Map<String, SettingInfo> result = new HashMap<>();
        List<SettingDefinition> allSettingDefinitions = _settingDefinitionManager.getAllSettingDefinitions();
        Map<SettingDefinition, SettingInfo> settingDefinitionStringMap = new HashMap<>();

        allSettingDefinitions.forEach(settingDefinition -> {
            Optional<SettingInfo> settingInfoOptional = settingValues.stream().filter(settingInfoItem -> settingInfoItem.getName().equals(settingDefinition.getName())).findFirst();
            String value = settingInfoOptional.isPresent() ? settingInfoOptional.get().getValue() : settingDefinition.getDefaultValue();
            settingDefinitionStringMap.put(settingDefinition, new SettingInfo(settingDefinition.getName(), value));
        });
        settingDefinitionStringMap.keySet().forEach(settingDefinition -> {
            result.put(settingDefinition.getName(), settingDefinitionStringMap.get(settingDefinition));
        });
        return result;
    }
}
