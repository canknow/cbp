package com.canknow.cbp.base.net.socket;

import java.util.UUID;

public interface ISocketService {
    void start();

    void stop();

    <T> void on(String messageName, Class<T> eventClass, SocketHandler<T> socketHandler);

    void send(UUID clientId, String eventName, Object... var2);
}

