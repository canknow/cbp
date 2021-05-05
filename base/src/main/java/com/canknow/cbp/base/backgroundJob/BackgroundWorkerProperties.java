package com.canknow.cbp.base.backgroundJob;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "background-worker")
public class BackgroundWorkerProperties {
    private Boolean disabled = false;
}
