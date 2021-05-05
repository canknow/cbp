package com.canknow.cbp.webCommon.application.notification;

import com.canknow.cbp.base.application.dto.CreationAuditedEntityDto;
import com.canknow.cbp.base.notification.Notification;
import com.canknow.cbp.base.notification.UserNotificationState;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserNotificationInfoDto extends CreationAuditedEntityDto {
    private String userId;

    private UserNotificationState state;

    private LocalDateTime creationTime;

    private Notification notification;
}
