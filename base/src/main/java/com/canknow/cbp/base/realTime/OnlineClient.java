package com.canknow.cbp.base.realTime;

import com.canknow.cbp.base.identifier.UserIdentifier;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class OnlineClient {
    private UUID connectionId;

    private String userId;

    private String ipAddress;

    private LocalDateTime connectTime;

    public static UserIdentifier toUserIdentifierOrNull(OnlineClient onlineClient) {
        return onlineClient.getUserId() != null
                ? new UserIdentifier(onlineClient.getUserId())
                : null;
    }
}
