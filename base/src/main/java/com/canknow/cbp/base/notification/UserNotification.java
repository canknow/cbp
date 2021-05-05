package com.canknow.cbp.base.notification;

import com.canknow.cbp.base.application.dto.Dto;
import com.canknow.cbp.base.identifier.IUserIdentifier;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserNotification extends Dto implements IUserIdentifier {
    private String userId;

    private UserNotificationState state;

    private LocalDateTime creationTime;

    private Notification notification;

    public UserNotification(String id, String userId, UserNotificationState state, LocalDateTime creationTime, Notification notification) {
        this.setId(id);
        this.userId = userId;
        this.state = state;
        this.creationTime = creationTime;
        this.notification = notification;
    }
}
