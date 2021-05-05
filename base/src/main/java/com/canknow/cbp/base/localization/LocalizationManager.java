package com.canknow.cbp.base.localization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocalizationManager implements ILocalizationManager {
    @Autowired
    private MessageSource messageSource;

    public String getLocalizationText(String name) {
        return getLocalizationText(name, null);
    }

    @Override
    public String getLocalizationText(String name, Object[] args) {
        try {
            Locale locale = LocaleContextHolder.getLocale();
            return messageSource.getMessage(name, args, locale);
        }
        catch (Exception e) {
            return name;
        }
    }
}
