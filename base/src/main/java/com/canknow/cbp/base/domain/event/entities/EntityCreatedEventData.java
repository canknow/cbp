package com.canknow.cbp.base.domain.event.entities;

public class EntityCreatedEventData<TEntity> extends EntityChangedEventData<TEntity> {
    public EntityCreatedEventData(TEntity tEntity) {
        super(tEntity);
    }
}
