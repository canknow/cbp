package com.canknow.cbp.base.notification;

import org.springframework.stereotype.Component;

import java.util.List;

@Component("nullRealTimeNotifier")
public class NullRealTimeNotifier implements IRealTimeNotifier {
    @Override
    public void sendNotifications(List<UserNotification> userNotifications) {

    }
}
