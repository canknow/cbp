package com.canknow.cbp.base.domain.event;

/**
 * 事件存储
 * Created by yyang on 15/4/23.
 */
public interface IEventStore {
    void store(DomainEvent event);
}
