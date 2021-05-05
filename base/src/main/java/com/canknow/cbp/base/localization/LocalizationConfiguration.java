package com.canknow.cbp.base.localization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocalizationConfiguration {
    @Bean("messageSource")
    public CbpReloadableResourceBundleMessageSource resourceBundleMessageSource() {
        CbpReloadableResourceBundleMessageSource resourceBundleMessageSource = new CbpReloadableResourceBundleMessageSource();
        resourceBundleMessageSource.setDefaultEncoding("UTF-8");
        resourceBundleMessageSource.addBasenames("classpath*:/i18n/messages");
        return resourceBundleMessageSource;
    }
}
