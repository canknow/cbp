package com.canknow.cbp.base.domain.entities;

import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;
import org.springframework.util.Assert;

import javax.persistence.MappedSuperclass;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@MappedSuperclass
@Data
public abstract class AggregateRoot extends EntityBase implements IAggregateRoot {
    private static final long serialVersionUID = -7363407452205475117L;
    @Transient
    private final transient List<Object> domainEvents = new ArrayList();

    @DomainEvents
    protected Collection<Object> domainEvents() {
        return Collections.unmodifiableList(this.domainEvents);
    }

    protected final <T extends AggregateRoot> void andEventsFrom(T aggregate) {
        Assert.notNull(aggregate, "Aggregate must not be null!");
        this.domainEvents.addAll(aggregate.domainEvents());
    }

    protected final void andEvent(Object event) {
        this.registerEvent(event);
    }

    protected <T> T registerEvent(T event) {
        Assert.notNull(event, "Domain event must not be null!");
        this.domainEvents.add(event);
        return event;
    }

    @AfterDomainEventPublication
    protected void clearDomainEvents() {
        this.domainEvents.clear();
    }
}
