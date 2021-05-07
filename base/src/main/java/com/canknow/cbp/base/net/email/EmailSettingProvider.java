package com.canknow.cbp.base.net.email;

import com.canknow.cbp.base.settings.ISettingProvider;
import com.canknow.cbp.base.settings.SettingDefinition;
import com.canknow.cbp.base.settings.SettingDefinitionProviderContext;
import com.canknow.cbp.base.settings.SettingScopes;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class EmailSettingProvider implements ISettingProvider {
    @Override
    public Collection<SettingDefinition> getSettingDefinitions(SettingDefinitionProviderContext context) {
        List<SettingDefinition> settingDefinitions = new ArrayList<>();

        settingDefinitions.add(new SettingDefinition(
                EmailSettingNames.DefaultFromAddress,
                "",
                SettingScopes.Application,
                true,
                true,
                null,
                false));
        settingDefinitions.add(new SettingDefinition(
                EmailSettingNames.DefaultFromDisplayName,
                "",
                SettingScopes.Application,
                true,
                true,
                null,
                false));

        settingDefinitions.add(new SettingDefinition(
                EmailSettingNames.Smtp.Host,
                "",
                SettingScopes.Application,
                true,
                true,
                null,
                false));
        settingDefinitions.add(new SettingDefinition(
                EmailSettingNames.Smtp.Port,
                "",
                SettingScopes.Application,
                true,
                true,
                null,
                false));
        settingDefinitions.add(new SettingDefinition(
                EmailSettingNames.Smtp.UserName,
                "",
                SettingScopes.Application,
                true,
                true,
                null,
                false));
        settingDefinitions.add(new SettingDefinition(
                EmailSettingNames.Smtp.Password,
                "",
                SettingScopes.Application,
                true,
                true,
                null,
                false));
        settingDefinitions.add(new SettingDefinition(
                EmailSettingNames.Smtp.Domain,
                "",
                SettingScopes.Application,
                true,
                true,
                null,
                false));
        settingDefinitions.add(new SettingDefinition(
                EmailSettingNames.Smtp.EnableSsl,
                "",
                SettingScopes.Application,
                true,
                true,
                null,
                false));
        settingDefinitions.add(new SettingDefinition(
                EmailSettingNames.Smtp.UseDefaultCredentials,
                "",
                SettingScopes.Application,
                true,
                true,
                null,
                false));
        return settingDefinitions;
    }
}
