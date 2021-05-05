package com.canknow.cbp.base.clientInfoProvider;

import org.springframework.stereotype.Component;

@Component("nullClientInfoProvider")
public class NullClientInfoProvider implements IClientInfoProvider {
    @Override
    public String getBrowserInfo() {
        return null;
    }

    @Override
    public String getClientIpAddress() {
        return null;
    }

    @Override
    public String getComputerName() {
        return null;
    }
}
