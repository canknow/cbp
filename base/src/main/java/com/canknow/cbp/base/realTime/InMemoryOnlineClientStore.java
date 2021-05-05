package com.canknow.cbp.base.realTime;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component("inMemoryOnlineClientStore")
public class InMemoryOnlineClientStore implements IOnlineClientStore {
    protected ConcurrentHashMap<UUID, OnlineClient> clients;

    public InMemoryOnlineClientStore() {
        clients = new ConcurrentHashMap<>();
    }

    @Override
    public void add(OnlineClient client) {
        clients.put(client.getConnectionId(), client);
    }

    @Override
    public void remove(UUID connectionId) {
        clients.remove(connectionId);
    }

    @Override
    public OnlineClient tryGet(UUID connectionId) {
        if(clients.containsKey(connectionId)) {
            return clients.get(connectionId);
        }
        return null;
    }

    @Override
    public Boolean contains(UUID connectionId) {
        return clients.containsKey(connectionId);
    }

    @Override
    public List<OnlineClient> getAll() {
        return new ArrayList<>(clients.values());
    }
}
