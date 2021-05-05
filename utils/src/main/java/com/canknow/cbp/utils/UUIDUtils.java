package com.canknow.cbp.utils;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UUIDUtils {
    public String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
