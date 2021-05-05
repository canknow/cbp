package com.canknow.cbp.base.domain.event.entities;

public class EntityChangedEventData<TEntity> extends EntityEventData<TEntity> {
    public EntityChangedEventData(TEntity tEntity) {
        super(tEntity);
    }
}
