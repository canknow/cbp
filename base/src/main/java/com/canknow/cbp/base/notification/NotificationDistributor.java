package com.canknow.cbp.base.notification;

import com.canknow.cbp.base.dependency.IIocResolver;
import com.canknow.cbp.base.identifier.UserIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NotificationDistributor implements INotificationDistributor {
    @Autowired(required = false)
    protected INotificationStore notificationStore;

    @Autowired
    protected IIocResolver iocResolver;

    @Override
    public void distribute(String notificationId) {
        NotificationInfo notificationInfo = notificationStore.getNotificationOrNull(notificationId);

        if (notificationInfo == null) {
            return;
        }
        List<UserIdentifier> users = getUsers(notificationInfo);
        List<UserNotification> userNotifications = saveUserNotifications(users, notificationInfo);
        notify(userNotifications);
    }

    protected List<UserIdentifier> getUsers(NotificationInfo notificationInfo) {
        List<UserIdentifier> userIds;

        userIds = Arrays.stream(notificationInfo
                .getUserIds()
                .split(","))
                .map(UserIdentifier::parse).collect(Collectors.toList());
        return userIds;
    }

    protected List<UserNotification> saveUserNotifications(List<UserIdentifier> users, NotificationInfo notificationInfo) {
        List<UserNotification> userNotifications = new ArrayList<>();
        Notification notification = notificationInfo.toNotification();

        users.forEach(user -> {
            UserNotificationInfo userNotificationInfo = new UserNotificationInfo(
                    notificationInfo.getId(),
                    user.getUserId(),
                    UserNotificationState.Unread);
            notificationStore.insertUserNotification(userNotificationInfo);
            userNotifications.add(userNotificationInfo.toUserNotification(notification));
        });
        return userNotifications;
    }

    protected void notify(List<UserNotification> userNotifications) {
        Map<String, IRealTimeNotifier> realTimeNotifiers = iocResolver.resolveAll(IRealTimeNotifier.class);

        realTimeNotifiers.forEach((s, realTimeNotifier) -> {
            realTimeNotifier.sendNotifications(userNotifications);
        });
    }
}
