package com.canknow.cbp.base.notification;

import java.util.List;

public interface IRealTimeNotifier {
    void sendNotifications(List<UserNotification> userNotifications);
}
