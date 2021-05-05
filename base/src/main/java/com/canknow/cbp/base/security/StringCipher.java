package com.canknow.cbp.base.security;

import com.canknow.cbp.base.security.IStringCipher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Component;

@Component
public class StringCipher implements IStringCipher {
    @Override
    public String encrypt(String plainText, Object salt) {
        SimpleHash simpleHash = new SimpleHash("MD5", plainText, salt, 1);
        return simpleHash.toString();
    }
}
