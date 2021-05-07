package com.canknow.cbp.base.settings;

public enum SettingScopes {
    Application,
    User,
    All;

    public boolean hasFlag(SettingScopes settingScopes) {
        return this.equals(SettingScopes.All) || this.equals(settingScopes);
    }
}
