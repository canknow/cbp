package com.canknow.cbp.base.notification;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class NotificationData {
    private Map<String, Object> properties = new HashMap<>();

    public void set(String key, Object object) {
        properties.put(key, object);
    }

    public Object get(String key) {
        return properties.get(key);
    }
}
