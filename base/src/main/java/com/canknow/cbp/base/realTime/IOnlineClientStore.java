package com.canknow.cbp.base.realTime;

import java.util.List;
import java.util.UUID;

public interface IOnlineClientStore {
    void add(OnlineClient client);

    void remove(UUID connectionId);

    OnlineClient tryGet(UUID connectionId);

    Boolean contains(UUID connectionId);

    List<OnlineClient> getAll();
}
