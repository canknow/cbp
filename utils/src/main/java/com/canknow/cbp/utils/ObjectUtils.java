package com.canknow.cbp.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;


@UtilityClass
public class ObjectUtils {
    /*
     * 获取泛型类Class对象，不是泛型类则返回null
     */
    public Class<?> getActualTypeArgument(Class<?> clazz) {
        Class<?> entityClass = null;
        Type genericSuperclass = clazz.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            if (actualTypeArguments != null && actualTypeArguments.length > 0) {
                entityClass = (Class<?>) actualTypeArguments[0];
            }
        }
        return entityClass;
    }

    public <T> T newTClass(Class<T> clazz) {
        T instance;
        try {
            instance = clazz.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
        return instance;
    }

    public <T> T mapToObject(Map<String, Object> map, Class<? extends T> tClass) {
        T bean = null;
        try {
            bean = tClass.newInstance();
            BeanUtils.populate(bean, map);
        }
        catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            return bean;
        }
        return bean;
    }
}
