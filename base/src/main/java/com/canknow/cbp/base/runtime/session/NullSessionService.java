package com.canknow.cbp.base.runtime.session;

import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component("nullSessionService")
public class NullSessionService implements ISessionService {
    @Override
    public void put(String key, String str, long expire) {

    }

    @Override
    public void put(String key, String str) {

    }

    @Override
    public void remove(String key) {

    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public <T> T get(String key, Type type) {
        return null;
    }
}
