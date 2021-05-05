package com.canknow.cbp.common.adapter.outbound.persistent.notification;

import com.canknow.cbp.base.domain.repositories.IRepository;
import com.canknow.cbp.base.notification.NotificationInfo;
import com.canknow.cbp.jpa.repositories.JpaRepositoryBase;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public class NotificationInfoRepository extends JpaRepositoryBase<NotificationInfo, INotificationInfoJpaRepository> implements IRepository<NotificationInfo> {
    public NotificationInfoRepository(INotificationInfoJpaRepository jpaRepository) {
        super(jpaRepository);
    }
}
