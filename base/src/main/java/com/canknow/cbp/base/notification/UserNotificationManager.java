package com.canknow.cbp.base.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserNotificationManager implements IUserNotificationManager {
    @Autowired
    private INotificationStore _store;

    @Override
    public Integer getUserNotificationCount(String userId, UserNotificationState state) {
        return _store.getUserNotificationCount(userId, state);
    }

    @Override
    public List<UserNotification> getUserNotifications(String userId, UserNotificationState state, Integer pageIndex, Integer pageSize) {
        List<UserNotificationInfoWithNotificationInfo> userNotifications =  _store.getUserNotificationsWithNotifications(userId, state, pageIndex, pageSize);
        return userNotifications.stream().map(UserNotificationInfoWithNotificationInfo::toUserNotification).collect(Collectors.toList());
    }

    @Override
    public void updateUserNotificationState(String userNotificationId, UserNotificationState state) {
         _store.updateUserNotificationState(userNotificationId, state);
    }

    @Override
    public void updateAllUserNotificationStates(String userId, UserNotificationState state) {
        _store.updateAllUserNotificationStates(userId, state);
    }

    @Override
    public UserNotification getUserNotification(String userId, String userNotificationId) {
        UserNotificationInfoWithNotificationInfo userNotification = _store.getUserNotificationWithNotificationOrNull(userNotificationId);

        if (userNotification == null) {
            return null;
        }
        return userNotification.toUserNotification();
    }
}
