package com.canknow.cbp.base.notification;

import com.canknow.cbp.base.identifier.UserIdentifier;

import java.util.List;

public interface INotificationPublisher {
    void publish(String notificationName, NotificationData data, List<UserIdentifier> userIds);
}
