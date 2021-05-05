package com.canknow.cbp.base.notification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private String notificationName;

    private Map notificationData;
}
