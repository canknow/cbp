package com.canknow.cbp.base.settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SettingDefinitionManager implements ISettingDefinitionManager {
    @Autowired
    private List<ISettingProvider> settingProviders;

    private final Map<String, SettingDefinition> _settings = new HashMap<>();

    public void initialize() {
        SettingDefinitionProviderContext context = new SettingDefinitionProviderContext(this);

        if (settingProviders != null) {
            settingProviders.forEach(provider -> {
                for (SettingDefinition settingDefinition : provider.getSettingDefinitions(context)) {
                    _settings.put(settingDefinition.getName(), settingDefinition);
                }
            });
        }
    }

    @Override
    public SettingDefinition getSettingDefinition(String name) {
        return _settings.get(name);
    }

    @Override
    public List<SettingDefinition> getAllSettingDefinitions() {
        return new ArrayList<>(_settings.values());
    }
}
