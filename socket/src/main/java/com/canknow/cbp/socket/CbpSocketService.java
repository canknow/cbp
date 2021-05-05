package com.canknow.cbp.socket;

import com.canknow.cbp.base.net.socket.ISocketService;
import com.canknow.cbp.base.net.socket.SocketHandler;
import com.canknow.cbp.base.net.socket.SocketMessageListener;
import com.canknow.cbp.base.realTime.IOnlineClientManager;
import com.canknow.cbp.base.realTime.OnlineClient;
import com.canknow.cbp.base.security.JWTUtils;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component("cbpSocketService")
@Primary
public class CbpSocketService implements ISocketService {
    private final Map<String, SocketIOClient> clientMap = new ConcurrentHashMap<>();

    @Autowired
    private SocketIOServer socketIOServer;

    @Autowired
    private IOnlineClientManager onlineClientManager;

    @PostConstruct
    private void autoStartup() {
        start();
    }

    /**
     * Spring IoC容器在销毁SocketIOServiceImpl Bean之前关闭,避免重启项目服务端口占用问题
     *
     */
    @PreDestroy
    private void autoStop() {
        stop();
    }

    @Override
    public void start() {
        socketIOServer.addConnectListener(client -> {
            String token = client.getHandshakeData().getSingleUrlParam("token");
            String userId = null;

            if (StringUtils.isNoneEmpty(token)) {
                userId = JWTUtils.getUserIdFromToken(token);
            }
            clientMap.put(client.getSessionId().toString(), client);
            OnlineClient onlineClient = new OnlineClient(client.getSessionId(), userId, null, LocalDateTime.now());
            onlineClientManager.add(onlineClient);
        });

        socketIOServer.addDisconnectListener(client -> {
            clientMap.remove(client.getSessionId().toString());
            onlineClientManager.remove(client.getSessionId());
            client.disconnect();
        });

        socketIOServer.addEventListener("login", Object.class, (client, data, ackSender) -> {
            String token = (String)data;
            String userId = null;

            if (StringUtils.isNoneEmpty(token)) {
                userId = JWTUtils.getUserIdFromToken(token);
            }
            OnlineClient onlineClient = onlineClientManager.get(client.getSessionId());

            if (onlineClient == null) {
                return;
            }
            onlineClient.setUserId(userId);
        });

        socketIOServer.addEventListener("logout", Object.class, (client, data, ackSender) -> {
            OnlineClient onlineClient = onlineClientManager.get(client.getSessionId());

            if (onlineClient == null) {
                return;
            }
            onlineClient.setUserId(null);
        });
        socketIOServer.start();
    }

    public <T> void on(String messageName, Class<T> eventClass, SocketHandler<T> socketHandler) {
        socketIOServer.addEventListener(messageName, eventClass, (client, data, ackSender) -> {
            socketHandler.handle(data);
            client.getHandshakeData();
        });
    }

    public void send(UUID clientId, String eventName, Object... var2) {
        socketIOServer.getClient(clientId).sendEvent(eventName, var2);
    }

    @Override
    public void stop() {
        if (socketIOServer != null) {
            socketIOServer.stop();
            socketIOServer = null;
        }
    }
}
