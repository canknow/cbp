package com.canknow.cbp.base.notification;

import java.util.List;

public interface INotificationStore {
    void insertNotification(NotificationInfo notification);

    NotificationInfo getNotificationOrNull(String notificationId);

    void deleteNotification(NotificationInfo notificationInfo);

    void insertUserNotification(UserNotificationInfo userNotificationInfo);

    Integer getUserNotificationCount(String userId, UserNotificationState state);

    List<UserNotificationInfoWithNotificationInfo> getUserNotificationsWithNotifications(String userId, UserNotificationState state, Integer pageIndex, Integer pageSize);

    void updateUserNotificationState(String userNotificationId, UserNotificationState state);

    void updateAllUserNotificationStates(String userId, UserNotificationState state);

    UserNotificationInfoWithNotificationInfo getUserNotificationWithNotificationOrNull(String userNotificationId);
}
