package com.canknow.cbp.webCommon.application.notification;

import com.canknow.cbp.base.application.dto.PagedResultDto;
import com.canknow.cbp.base.notification.UserNotification;
import lombok.Data;

import java.util.List;

@Data
public class GetNotificationsOutput extends PagedResultDto<UserNotification> {
    private Integer unreadCount;

    public GetNotificationsOutput (Integer totalCount, Integer unreadCount, List<UserNotification> notifications) {
        this.setTotalCount(totalCount);
        this.unreadCount = unreadCount;
        this.setItems(notifications);
    }
}
