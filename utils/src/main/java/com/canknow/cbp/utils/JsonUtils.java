package com.canknow.cbp.utils;

import com.alibaba.fastjson.JSON;
import lombok.experimental.UtilityClass;
import net.sf.json.JSONObject;

import java.util.Map;

@UtilityClass
public class JsonUtils {
    public String mapToJson(Map<String, String> data) {
        JSONObject jsonObject = JSONObject.fromObject(data);
        return jsonObject.toString();
    }

    public Map jsonToMap(String json) {
        return JSON.parseObject(json);
    }

    public String objectToJson(Object object) {
        return JSON.toJSONString(object);
    }

    public Object jsonToObject(String jsonString, Class<?> tClass) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        return JSONObject.toBean(jsonObject, tClass);
    }
}
