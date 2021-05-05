package com.canknow.cbp.base.domain.event;

import org.springframework.context.ApplicationEvent;

public abstract class DomainEvent extends ApplicationEvent {

    public DomainEvent(Object source) {
        super(source);
    }
}
