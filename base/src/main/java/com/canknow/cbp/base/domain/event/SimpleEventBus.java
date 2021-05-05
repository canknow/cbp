package com.canknow.cbp.base.domain.event;

import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public final class SimpleEventBus implements IEventBus {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEventBus.class);

    private final IEventStore eventStore;

    private List<IEventHandler> eventHandlers = new ArrayList<>();

    public SimpleEventBus(IEventStore eventStore) {
        this.eventStore = eventStore;
    }

    public SimpleEventBus(IEventStore eventStore, List<IEventHandler> listeners) {
        Assert.notNull(eventStore, "Event Store is null.");
        this.eventStore = eventStore;
        Assert.notEmpty(listeners, "listeners must not be null or empty.");
        this.eventHandlers = Collections.unmodifiableList(listeners);
    }

    List<IEventHandler> getEventHandlers() {
        return eventHandlers;
    }

    @Override
    public void on(IEventHandler... listeners) {
        this.eventHandlers.addAll(Arrays.asList(listeners));
    }

    @Override
    public void off(IEventHandler... listeners) {
        this.eventHandlers.removeAll(Arrays.asList(listeners));
    }

    @Override
    public void trigger(DomainEvent event) {
        eventStore.store(event);
        for (IEventHandler listener : eventHandlers) {
            listener.handle(event);
        }
    }
}
