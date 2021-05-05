package com.canknow.cbp.base.net.socket;

public interface SocketMessageListener<T> {
    void handle(String messageName, T messageData);
}
