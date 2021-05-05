package com.canknow.cbp.base.utils;

import com.canknow.cbp.base.security.IStringCipher;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PasswordUtil {
    @Autowired
    private IStringCipher stringCipher;

    public String encrypt(String userName, String password) {
        Object salt = ByteSource.Util.bytes(userName);
        return stringCipher.encrypt(password, salt);
    }
}
