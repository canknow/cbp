package com.canknow.cbp.base.security;

public interface IStringCipher {
    String encrypt(String plainText, Object salt);
}
