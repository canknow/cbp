package com.canknow.cbp.base.net.socket;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("nullSocketService")
public class NullSocketService implements ISocketService {
    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public <T> void on(String messageName, Class<T> eventClass, SocketHandler<T> socketHandler) {

    }

    @Override
    public void send(UUID clientId, String eventName, Object... var2) {

    }
}
