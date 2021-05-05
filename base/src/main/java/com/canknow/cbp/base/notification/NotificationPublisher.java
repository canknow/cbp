package com.canknow.cbp.base.notification;

import com.canknow.cbp.base.exceptions.InvalidParameterException;
import com.canknow.cbp.base.identifier.UserIdentifier;
import com.canknow.cbp.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NotificationPublisher implements INotificationPublisher {
    public static int MaxUserCountToDirectlyDistributeANotification = 5;

    @Autowired(required = false)
    protected INotificationDistributor notificationDistributor;

    @Autowired(required = false)
    protected INotificationStore notificationStore;

    @Override
    public void publish(String notificationName, NotificationData notificationData, List<UserIdentifier> userIds) {
        if (StringUtils.isBlank(notificationName)) {
            throw new InvalidParameterException("NotificationName can not be null or whitespace!");
        }

        NotificationInfo notificationInfo = new NotificationInfo(
                notificationName,
                JsonUtils.objectToJson(notificationData),
                userIds.stream().map(UserIdentifier::getUserId).collect(Collectors.joining(",")));

        notificationStore.insertNotification(notificationInfo);
        notificationDistributor.distribute(notificationInfo.getId());
    }
}
