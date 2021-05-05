package com.canknow.cbp.base.settings;

public enum SettingScopes {
    Application,
    Tenant,
    User,
    All;

    public boolean hasFlag(SettingScopes settingScopes) {
        return this.equals(SettingScopes.All) || this.equals(settingScopes);
    }
}
