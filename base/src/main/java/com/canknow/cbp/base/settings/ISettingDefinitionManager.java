package com.canknow.cbp.base.settings;

import java.util.List;

public interface ISettingDefinitionManager {
    SettingDefinition getSettingDefinition(String name);

    List<SettingDefinition> getAllSettingDefinitions();
}
