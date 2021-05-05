package com.canknow.cbp.base.settings;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class EmptySettingProvider implements ISettingProvider{
    @Override
    public Collection<SettingDefinition> getSettingDefinitions(SettingDefinitionProviderContext context) {
        return new ArrayList<>();
    }
}
