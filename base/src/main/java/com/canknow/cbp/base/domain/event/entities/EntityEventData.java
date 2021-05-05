package com.canknow.cbp.base.domain.event.entities;

import com.canknow.cbp.base.domain.event.DomainEvent;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;
import org.springframework.data.util.ProxyUtils;

public class EntityEventData<TEntity> extends DomainEvent implements ResolvableTypeProvider {
    public EntityEventData(TEntity entity) {
        super(entity);
    }

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ProxyUtils.getUserClass(getSource()));
    }
}
