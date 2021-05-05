package com.canknow.cbp.base.notification;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("nullNotificationStore")
public class NullNotificationStore implements INotificationStore {
    @Override
    public void insertNotification(NotificationInfo notification) {

    }

    @Override
    public NotificationInfo getNotificationOrNull(String notificationId) {
        return null;
    }

    @Override
    public void deleteNotification(NotificationInfo notificationInfo) {

    }

    @Override
    public void insertUserNotification(UserNotificationInfo userNotificationInfo) {

    }

    @Override
    public Integer getUserNotificationCount(String userId, UserNotificationState state) {
        return null;
    }

    @Override
    public List<UserNotificationInfoWithNotificationInfo> getUserNotificationsWithNotifications(String userId, UserNotificationState state, Integer pageIndex, Integer pageSize) {
        return null;
    }

    @Override
    public void updateUserNotificationState(String userNotificationId, UserNotificationState state) {

    }

    @Override
    public void updateAllUserNotificationStates(String userId, UserNotificationState state) {

    }

    @Override
    public UserNotificationInfoWithNotificationInfo getUserNotificationWithNotificationOrNull(String userNotificationId) {
        return null;
    }
}
