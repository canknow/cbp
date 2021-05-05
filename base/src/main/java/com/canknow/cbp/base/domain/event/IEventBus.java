package com.canknow.cbp.base.domain.event;

public interface IEventBus {

    /**
     * 注册事件处理器
     * @param handlers 要注册的事件处理器
     */
    void on(IEventHandler... handlers);

    /**
     * 卸载事件处理器
     * @param handlers 要卸载的事件处理器
     */
    void off(IEventHandler... handlers);

    /**
     * 接收领域事件
     * @param event 要接收的领域事件
     */
    void trigger(DomainEvent event);
}
