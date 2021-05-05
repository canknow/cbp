package com.canknow.cbp.base.domain.event.entities;

public class EntityUpdatedEventData<TEntity> extends EntityChangedEventData<TEntity> {
    public EntityUpdatedEventData(TEntity tEntity) {
        super(tEntity);
    }
}
