package com.canknow.cbp.base.settings;

import com.canknow.cbp.base.localization.ILocalizableString;
import lombok.Data;

@Data
public class SettingDefinition {
    private String name;

    private ILocalizableString displayName;

    private ILocalizableString description;

    private String defaultValue;

    private Object customData;

    private SettingScopes scopes;

    private boolean isInherited;

    private boolean isEncrypted;

    private ISettingClientVisibilityProvider clientVisibilityProvider;

    public SettingDefinition(String name,
                             String defaultValue,
                             SettingScopes scopes,
                             boolean isVisibleToClients,
                             boolean isInherited,
                             ISettingClientVisibilityProvider clientVisibilityProvider,
                             boolean isEncrypted) {
        this.name = name;
        this.defaultValue = defaultValue;
        this.scopes = scopes;
        this.isInherited = isInherited;
        this.isEncrypted = isEncrypted;

        clientVisibilityProvider = new HiddenSettingClientVisibilityProvider();

        if (isVisibleToClients) {
            this.clientVisibilityProvider = new VisibleSettingClientVisibilityProvider();
        }
        else {
            this.clientVisibilityProvider = clientVisibilityProvider;
        }
    }
}
