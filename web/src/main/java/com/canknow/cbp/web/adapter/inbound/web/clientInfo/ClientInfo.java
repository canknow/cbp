package com.canknow.cbp.web.adapter.inbound.web.clientInfo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientInfo implements Serializable {
    private static final long serialVersionUID = -5549531244606897514L;

    private String ip;

    private String addressIp;

    private String browserName;

    private String browserVersion;

    private String engineName;

    private String engineVersion;

    private String osName;

    private String platformName;

    private boolean mobile;

    private String deviceName;

    private String deviceModel;
}
