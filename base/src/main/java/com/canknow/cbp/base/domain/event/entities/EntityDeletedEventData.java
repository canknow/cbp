package com.canknow.cbp.base.domain.event.entities;

public class EntityDeletedEventData<TEntity> extends EntityChangedEventData<TEntity> {
    public EntityDeletedEventData(TEntity entity) {
        super(entity);
    }
}
