package com.canknow.cbp.base.enums;

import org.springframework.stereotype.Component;

@Component("cbpApplicationPackageProvider")
public class CbpApplicationPackageProvider implements IApplicationPackageProvider {
    @Override
    public String getName() {
        return "com.canknow";
    }
}
