package com.canknow.cbp.webCommon.application.notification;

import com.canknow.cbp.base.application.dto.GetAllByPageInput;
import com.canknow.cbp.base.notification.UserNotificationState;
import lombok.Data;

@Data
public class GetUserNotificationsInput extends GetAllByPageInput {
    private UserNotificationState state;
}
