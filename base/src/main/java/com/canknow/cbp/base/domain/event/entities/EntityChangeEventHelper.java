package com.canknow.cbp.base.domain.event.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EntityChangeEventHelper {
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public <TEntity> void triggerEntityCreatedEvent(TEntity entity) {
        EntityCreatedEventData<TEntity> eventData = new EntityCreatedEventData<>(entity);
        eventPublisher.publishEvent(eventData);
    }

    public <TEntity> void triggerEntityUpdatedEvent(TEntity entity) {
        EntityUpdatedEventData<TEntity> eventData = new EntityUpdatedEventData<>(entity);
        eventPublisher.publishEvent(eventData);
    }

    public <TEntity> void triggerEntityDeletedEvent(TEntity entity) {
        EntityDeletedEventData<TEntity> eventData = new EntityDeletedEventData<>(entity);
        eventPublisher.publishEvent(eventData);
    }
}
