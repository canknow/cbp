package com.canknow.cbp.base.realTime;

import com.canknow.cbp.base.identifier.IUserIdentifier;
import com.canknow.cbp.base.identifier.UserIdentifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OnlineClientManager implements IOnlineClientManager {
    @Autowired
    protected IOnlineClientStore onlineClientStore;

    @Override
    public void add(OnlineClient onlineClient) {
        Boolean userWasAlreadyOnline = false;
        UserIdentifier user = OnlineClient.toUserIdentifierOrNull(onlineClient);

        if (user != null) {
            userWasAlreadyOnline = this.isOnline(user);
        }

        onlineClientStore.add(onlineClient);
    }

    public Boolean isOnline(UserIdentifier userIdentifier) {
        return getAllByUserId(userIdentifier).size() > 0;
    }

    @Override
    public void remove(UUID connectionId) {
        onlineClientStore.remove(connectionId);
    }

    @Override
    public OnlineClient get(UUID connectionId) {
        return onlineClientStore.tryGet(connectionId);
    }

    @Override
    public OnlineClient getByConnectionIdOrNull(UUID connectionId) {
        return onlineClientStore.tryGet(connectionId);
    }

    @Override
    public List<OnlineClient> getAllClients() {
        return onlineClientStore.getAll();
    }

    @Override
    public List<OnlineClient> getAllByUserId(IUserIdentifier userIdentifier) {
        return getAllClients().stream().filter(onlineClient -> userIdentifier.getUserId().equals(onlineClient.getUserId())).collect(Collectors.toList());
    }

    @Override
    public boolean isOnline(String userId) {
        return !getAllByUserId(new UserIdentifier(userId)).isEmpty();
    }
}
