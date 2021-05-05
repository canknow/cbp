package com.canknow.cbp.webCommon.application.notification;

import com.canknow.cbp.base.application.applicationService.GetApplicationService;
import com.canknow.cbp.base.application.dto.FindInput;
import com.canknow.cbp.base.application.dto.GetAllInput;
import com.canknow.cbp.base.application.dto.IdInput;
import com.canknow.cbp.base.notification.IUserNotificationManager;
import com.canknow.cbp.base.notification.UserNotification;
import com.canknow.cbp.base.notification.UserNotificationInfo;
import com.canknow.cbp.base.notification.UserNotificationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationApplicationService extends GetApplicationService<UserNotificationInfo, UserNotificationInfoDto, UserNotificationInfoDto, UserNotificationInfoDto, FindInput, GetAllInput, GetUserNotificationsInput> {
    @Autowired
    private IUserNotificationManager userNotificationManager;

    public GetNotificationsOutput getUserNotifications(GetUserNotificationsInput input) {
        Integer totalCount =  userNotificationManager.getUserNotificationCount(applicationSession.getUserId(), input.getState());
        Integer unreadCount = userNotificationManager.getUserNotificationCount(applicationSession.getUserId(), UserNotificationState.Unread);

        List<UserNotification> notifications = userNotificationManager.getUserNotifications(
                applicationSession.getUserId(),
                input.getState(),
                input.getPageIndex(),
                input.getPageSize()
        );

        return new GetNotificationsOutput(totalCount, unreadCount, notifications);
    }

    public void setAllNotificationsAsRead() {
        userNotificationManager.updateAllUserNotificationStates(applicationSession.getUserId(), UserNotificationState.Read);
    }

    public void setNotificationAsRead(IdInput input) throws Exception {
        UserNotification userNotification = userNotificationManager.getUserNotification(applicationSession.getUserId(), input.getId());

        if (!userNotification.getUserId().equals(applicationSession.getUserId())) {
            throw new Exception(String.format("Given user notification id ({0}) is not belong to the current user ({1})", input.getId(), applicationSession.getUserId()));
        }
        userNotificationManager.updateUserNotificationState(input.getId(), UserNotificationState.Read);
    }
}
