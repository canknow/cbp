package com.canknow.cbp.base.localization;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class LocalizationContext implements ILocalizationContext {
    @Autowired
    public ILocalizationManager localizationManager;
}
