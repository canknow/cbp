package com.canknow.cbp.base.settings;

import java.util.Collection;

public interface ISettingProvider {
    Collection<SettingDefinition> getSettingDefinitions(SettingDefinitionProviderContext context);
}
