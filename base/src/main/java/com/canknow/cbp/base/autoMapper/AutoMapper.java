package com.canknow.cbp.base.autoMapper;

import com.canknow.cbp.utils.ReflectUtil;
import lombok.experimental.UtilityClass;
import org.springframework.util.CollectionUtils;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class AutoMapper {
    public <S, T> T mapTo(S source, Class<T> tClass) {
        if (source == null) {
            return null;
        }

        T target = null;
        try {
            target = tClass.newInstance();
            mapTo(source, target);
        }
        catch (InstantiationException | IllegalAccessException e) {
            return target;
        }
        return target;
    }

    public <S, T> List<T> mapListTo(List<S> source, Class<T> tClass) {
        if (CollectionUtils.isEmpty(source)){
            return new ArrayList<>();
        }
        return source.stream().map((S item) -> AutoMapper.mapTo(item, tClass)).collect(Collectors.toList());
    }

    public void mapTo(Object source, Object target) {
        Class<?> targetClass = target.getClass();
        List<Field> targetClassFields = ReflectUtil.recursiveGetClassFields(new ArrayList<>(), targetClass);

        try {
            for (Field targetClassField : targetClassFields) {
                Object sourceValue = null;

                String targetClassFieldName = targetClassField.getName();
                AutoMap autoMap = targetClassField.getAnnotation(AutoMap.class);

                Method readMethod;

                try {
                    String readMethodName = isLowerBoolean(targetClassField, targetClassFieldName) ? targetClassFieldName : ReflectUtil.getMethodName("get", targetClassFieldName);
                    PropertyDescriptor readPropertyDescriptor = new PropertyDescriptor(targetClassFieldName, source.getClass(), readMethodName, null);
                    readMethod = readPropertyDescriptor.getReadMethod();
                }
                catch (IntrospectionException e) {
                    continue;
                }
                String writeMethodName = isLowerBoolean(targetClassField, targetClassFieldName) ? targetClassFieldName.replace("is", "set") : ReflectUtil.getMethodName("set", targetClassFieldName);
                PropertyDescriptor writePropertyDescriptor = new PropertyDescriptor(targetClassFieldName, target.getClass(), null, writeMethodName);
                Method writeMethod = writePropertyDescriptor.getWriteMethod();

                if (writeMethod == null) {
                    continue;
                }

                try {
                    sourceValue = readMethod.invoke(source);
                }
                catch (Exception e) {
                    System.console().printf(e.getMessage());
                }

                if (autoMap == null) {
                    writeMethod.invoke(target, sourceValue);
                }
                else if (targetClassField.getType() == Set.class) {
                    Set<?> sourceItems = (Set<?>) sourceValue;

                    if (sourceItems != null) {
                        Set targetItems = new HashSet();

                        for (Object sourceItem : sourceItems) {
                            Object targetItem = null;
                            targetItem = autoMap.type().newInstance();
                            mapTo(sourceItem, targetItem);
                            targetItems.add(targetItem);
                        }
                        writeMethod.invoke(target, targetItems);
                    }
                }
                // 如果是列表
                else if (targetClassField.getType() == List.class) {
                    List<?> sourceItems = (List<?>) sourceValue;

                    if (sourceItems != null) {
                        List targetItems = new ArrayList<>();

                        for (Object sourceItem : sourceItems) {
                            Object targetItem = null;
                            targetItem = autoMap.type().newInstance();
                            mapTo(sourceItem, targetItem);
                            targetItems.add(targetItem);
                        }
                        writeMethod.invoke(target, targetItems);
                    }
                }
                else if (sourceValue != null) {
                    Object targetObject = autoMap.type().newInstance();
                    mapTo(sourceValue, targetObject);
                    writeMethod.invoke(target, targetObject);
                }
            }
        }
        catch (Exception e) {
            System.console().printf(e.getMessage());
        }
    }

    private static boolean isLowerBoolean(Field classField, String fieldName) {
        return fieldName.startsWith("is") && classField.getType() == boolean.class;
    }
}
