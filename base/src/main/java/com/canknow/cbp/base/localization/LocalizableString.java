package com.canknow.cbp.base.localization;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LocalizableString implements ILocalizableString {
    private final String name;

    @Override
    public String localize(ILocalizationContext context) {
        return context.getLocalizationManager().getLocalizationText(name);
    }
}
