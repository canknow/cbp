package com.canknow.cbp.base.notification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNotificationInfoWithNotificationInfo {
    UserNotificationInfo userNotification;

    NotificationInfo notification;

    public UserNotification toUserNotification() {
        return userNotification.toUserNotification(notification.toNotification());
    }
}
