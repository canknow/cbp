package com.canknow.cbp.common.adapter.outbound.persistent.notification;

import com.canknow.cbp.base.notification.UserNotificationInfo;
import com.canknow.cbp.base.notification.UserNotificationState;
import com.canknow.cbp.jpa.repositories.JpaRepositoryBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserNotificationInfoRepository extends JpaRepositoryBase<UserNotificationInfo, IUserNotificationInfoJpaRepository> implements IUserNotificationRepository {
    public UserNotificationInfoRepository(IUserNotificationInfoJpaRepository jpaRepository) {
        super(jpaRepository);
    }

    @Override
    public int countByStateAndUserId(UserNotificationState state, String userId) {
        return jpaRepository.countByStateAndUserId(state, userId);
    }

    @Override
    public int countByUserId(String userId) {
        return jpaRepository.countByUserId(userId);
    }

    @Override
    public List<UserNotificationInfo> findAllByUserId(String userId) {
        return jpaRepository.findAllByUserId(userId);
    }

    @Override
    public Page<UserNotificationInfo> findAllByUserId(String userId, Pageable pageable) {
        return jpaRepository.findAllByUserIdOrderByCreationTimeDesc(userId, pageable);
    }
}
