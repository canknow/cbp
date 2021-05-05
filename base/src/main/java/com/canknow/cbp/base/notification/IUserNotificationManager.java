package com.canknow.cbp.base.notification;

import java.util.List;

public interface IUserNotificationManager {
    Integer getUserNotificationCount(String userId, UserNotificationState state);

    List<UserNotification> getUserNotifications(String userId, UserNotificationState state, Integer pageIndex, Integer pageSize);

    void updateUserNotificationState(String userNotificationId, UserNotificationState state);

    void updateAllUserNotificationStates(String userId, UserNotificationState read);

    UserNotification getUserNotification(String userId, String id);
}
