package com.canknow.cbp.base.enums;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

@Component
public class CommonEnumConverterFactory implements ConverterFactory<String, BaseEnum> {
    private static final Map<Class, Converter> converterMap = new WeakHashMap<>();

    @Override
    public <T extends BaseEnum> Converter<String, T> getConverter(Class<T> targetType) {
        Converter result = converterMap.get(targetType);

        if(result == null) {
            result = new IntegerStringToEnum<T>(targetType);
            converterMap.put(targetType, result);
        }
        return result;
    }

    class IntegerStringToEnum<T extends BaseEnum> implements Converter<String, T> {
        private final Class<T> enumType;
        private Map<String, T> enumMap = new HashMap<>();

        public IntegerStringToEnum(Class<T> enumType) {
            this.enumType = enumType;
            T[] enums = enumType.getEnumConstants();

            for(T e : enums) {
                enumMap.put(e.getCode() + "", e);
            }
        }


        @Override
        public T convert(String source) {
            T result = enumMap.get(source);

            if(result == null) {
                throw new IllegalArgumentException("No element matches " + source);
            }
            return result;
        }
    }
}