package com.canknow.cbp.common.adapter.outbound.persistent.notification;

import com.canknow.cbp.base.domain.repositories.IRepository;
import com.canknow.cbp.base.notification.UserNotificationInfo;
import com.canknow.cbp.base.notification.UserNotificationState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserNotificationRepository extends IRepository<UserNotificationInfo> {
    int countByStateAndUserId(UserNotificationState state, String userId);

    int countByUserId(String userId);

    List<UserNotificationInfo> findAllByUserId(String userId);

    Page<UserNotificationInfo> findAllByUserId(String userId, Pageable pageable);
}
