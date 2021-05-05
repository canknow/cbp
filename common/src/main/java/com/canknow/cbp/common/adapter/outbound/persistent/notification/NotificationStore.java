package com.canknow.cbp.common.adapter.outbound.persistent.notification;

import com.canknow.cbp.base.domain.repositories.IRepository;
import com.canknow.cbp.base.notification.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("notificationStore")
@Primary
public class NotificationStore implements INotificationStore {
    @Autowired
    private IRepository<NotificationInfo> notificationRepository;
    @Autowired
    private IUserNotificationRepository userNotificationRepository;

    @Override
    public void insertNotification(NotificationInfo notification) {
        notificationRepository.insert(notification);
    }

    @Override
    public NotificationInfo getNotificationOrNull(String notificationId) {
        return notificationRepository.get(notificationId);
    }

    @Override
    public void deleteNotification(NotificationInfo notificationInfo) {
        notificationRepository.delete(notificationInfo);
    }

    @Override
    public void insertUserNotification(UserNotificationInfo userNotificationInfo) {
        userNotificationRepository.insert(userNotificationInfo);
    }

    @Override
    public Integer getUserNotificationCount(String userId, UserNotificationState state) {
        if (state == null) {
            return userNotificationRepository.countByUserId(userId);
        }
        return userNotificationRepository.countByStateAndUserId(state, userId);
    }

    @Override
    public void updateUserNotificationState(String userNotificationId, UserNotificationState state) {
        UserNotificationInfo userNotification = userNotificationRepository.get(userNotificationId);

        if (userNotification == null) {
            return;
        }
        userNotification.setState(state);
        userNotificationRepository.update(userNotification);
    }

    @Override
    public void updateAllUserNotificationStates(String userId, UserNotificationState state) {
        List<UserNotificationInfo> userNotifications = userNotificationRepository.findAllByUserId(userId);
        userNotifications.forEach(userNotificationInfo -> {
            userNotificationInfo.setState(state);
        });
        userNotificationRepository.updateAll(userNotifications);
    }

    @Override
    public List<UserNotificationInfoWithNotificationInfo> getUserNotificationsWithNotifications(String userId,
                                                                                                UserNotificationState state,
                                                                                                Integer pageIndex,
                                                                                                Integer pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<UserNotificationInfo> page = userNotificationRepository.findAllByUserId(userId, pageable);
        return page.getContent().stream().map(userNotificationInfo -> {
            return new UserNotificationInfoWithNotificationInfo(userNotificationInfo, userNotificationInfo.getNotification());
        }).collect(Collectors.toList());
    }

    @Override
    public UserNotificationInfoWithNotificationInfo getUserNotificationWithNotificationOrNull(String userNotificationId) {
        UserNotificationInfo userNotificationInfo = userNotificationRepository.get(userNotificationId);
        return new UserNotificationInfoWithNotificationInfo(userNotificationInfo, userNotificationInfo.getNotification());
    }
}
