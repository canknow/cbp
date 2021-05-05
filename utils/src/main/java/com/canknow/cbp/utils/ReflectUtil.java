package com.canknow.cbp.utils;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class ReflectUtil {
    public List<Field> recursiveGetClassFields(List<Field> list, Class<?> clazz) {
        if (clazz == null) {
            return list;
        }
        list.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return recursiveGetClassFields(list, clazz.getSuperclass());
    }

    public Method getWriteMethod(Object object, String name) {
        Method[] methods = object.getClass().getMethods();

        for (Method method : methods) {
            if (getMethodName("set", name).equals(method.getName())) {
                return method;
            }
        }
        return null;
    }

    public Method getReadMethod(Object object, String name) {
        Method[] methods = object.getClass().getMethods();

        for (Method method : methods) {
            if (getMethodName("get", name).equals(method.getName())) {
                return method;
            }
        }
        return null;
    }

    public String getMethodName(String prefix, String fieldName) {
        return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public Method getMethod(Class<?> tClass, String methodName) {
        Method[] methods = tClass.getMethods();

        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                return method;
            }
        }
        return null;
    }

    // 获得类的全名，包括包名
    public String getPackPath(Object object) {
        // 检查用户传入的参数是否为空
        if (object == null) {
            throw new java.lang.IllegalArgumentException("参数不能为空！");
        }
        // 获得类的全名，包括包名
        String clsName = object.getClass().getName();
        return clsName;
    }
}
