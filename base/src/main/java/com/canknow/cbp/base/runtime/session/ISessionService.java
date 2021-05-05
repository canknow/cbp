package com.canknow.cbp.base.runtime.session;

import java.lang.reflect.Type;

public interface ISessionService {
    void put(String key, String str, long expire);

    void put(String key, String str);

    void remove(String key);

    Object get(String key);

    <T> T get(String key, Type type);
}
