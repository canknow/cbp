package com.canknow.cbp.base.net.socket;

public interface SocketHandler<T> {
    void handle(T messageData);
}
