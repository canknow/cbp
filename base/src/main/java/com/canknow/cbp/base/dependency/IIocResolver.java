package com.canknow.cbp.base.dependency;

import java.util.Map;

public interface IIocResolver {
    <T> T register(String name, Class<T> tClass, Object... args);

    <T> T replace(String name, Class<T> tClass, Object... args);

    <T> T resolve(Class<T> clazz);

    <T> T resolveByClassName(String className);

    Object resolve(String name);

    <T> T resolve(String name, Class<T> clazz);

    <T> Map<String, T> resolveAll(Class<T> clazz);
}
