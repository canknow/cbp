package com.canknow.cbp.base.notification;

import com.canknow.cbp.base.domain.entities.audit.CreationAuditedEntityBase;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@NamedEntityGraph(
        name = "userNotificationInfo.getAllByPage",
        includeAllAttributes = true)
public class UserNotificationInfo extends CreationAuditedEntityBase {
    @Column(length = 64)
    private String notificationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notificationId", insertable = false, updatable = false, nullable = true)
    private NotificationInfo notification;

    @Column(length = 64)
    private String userId;

    private UserNotificationState state;

    public UserNotificationInfo(String notificationId, String userId, UserNotificationState state) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.state = state;
    }

    @Transient
    public UserNotification toUserNotification(Notification notification) {
        return new UserNotification(getId(), userId, state, getCreationTime(), notification);
    }
}
