package com.canknow.cbp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JacksonJsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String objectToJson(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T jsonToObject(String json, Class<T> tClass) {
        try {
            T t = MAPPER.readValue(json, tClass);
            return t;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> jsonToList(String json, Class<T> tClass) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, tClass);
        try {
            return MAPPER.readValue(json, javaType);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}