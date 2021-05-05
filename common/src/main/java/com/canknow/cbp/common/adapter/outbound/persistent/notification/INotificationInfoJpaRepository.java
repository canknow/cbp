package com.canknow.cbp.common.adapter.outbound.persistent.notification;

import com.canknow.cbp.base.notification.NotificationInfo;
import com.canknow.cbp.jpa.repositories.IJpaRepositoryBase;
import org.springframework.stereotype.Repository;

@Repository
public interface INotificationInfoJpaRepository extends IJpaRepositoryBase<NotificationInfo> {
}
