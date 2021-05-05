package com.canknow.cbp.socket.notification;

import com.alibaba.fastjson.JSON;
import com.canknow.cbp.base.notification.IRealTimeNotifier;
import com.canknow.cbp.base.notification.UserNotification;
import com.canknow.cbp.base.realTime.IOnlineClientManager;
import com.canknow.cbp.base.realTime.OnlineClient;
import com.canknow.cbp.base.net.socket.ISocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "socketRealTimeNotifier")
@Primary
public class SocketRealTimeNotifier implements IRealTimeNotifier {
    @Autowired
    private IOnlineClientManager onlineClientManager;
    @Autowired
    private ISocketService socketIOServer;

    @Override
    public void sendNotifications(List<UserNotification> userNotifications) {
        userNotifications.forEach(userNotification -> {
            List<OnlineClient> onlineClients = onlineClientManager.getAllByUserId(userNotification);

            onlineClients.forEach(onlineClient -> {
                socketIOServer.send(onlineClient.getConnectionId(), "notification", JSON.toJSONString(userNotification.getNotification()));
            });
        });
    }
}
