package com.canknow.cbp.base.net.email;

import com.canknow.cbp.base.settings.ISettingProvider;
import com.canknow.cbp.base.settings.SettingDefinition;
import com.canknow.cbp.base.settings.SettingDefinitionProviderContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class EmailSettingProvider implements ISettingProvider {
    @Override
    public Collection<SettingDefinition> getSettingDefinitions(SettingDefinitionProviderContext context) {
        return new ArrayList<>();
    }
}
