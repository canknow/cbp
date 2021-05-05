package com.canknow.cbp.base.realTime;

import com.canknow.cbp.base.identifier.IUserIdentifier;

import java.util.List;
import java.util.UUID;

public interface IOnlineClientManager {
    void add(OnlineClient client);

    void remove(UUID connectionId);

    OnlineClient get(UUID connectionId);

    OnlineClient getByConnectionIdOrNull(UUID connectionId);

    List<OnlineClient> getAllClients();

    List<OnlineClient> getAllByUserId(IUserIdentifier user);

    boolean isOnline(String userId);
}
