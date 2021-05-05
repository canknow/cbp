package com.canknow.cbp.web.adapter.inbound.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application")
public class ApplicationConfig {
    private String uploadPath;

    /**
     * 资源访问路径，前端访问
     */
    private String resourceAccessPath;

    private String resourceAccessPatterns;

    private String resourceAccessUrl;
}
