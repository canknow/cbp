package com.canknow.cbp.common.adapter.outbound.persistent.notification;

import com.canknow.cbp.base.notification.UserNotificationInfo;
import com.canknow.cbp.base.notification.UserNotificationState;
import com.canknow.cbp.jpa.repositories.IJpaRepositoryBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserNotificationInfoJpaRepository extends IJpaRepositoryBase<UserNotificationInfo> {
    int countByStateAndUserId(UserNotificationState state, String userId);

    int countByUserId(String userId);

    List<UserNotificationInfo> findAllByUserId(String userId);

    @EntityGraph(value = "userNotificationInfo.getAllByPage", type = EntityGraph.EntityGraphType.FETCH)
    Page<UserNotificationInfo> findAllByUserIdOrderByCreationTimeDesc(String userId, Pageable pageable);

    @EntityGraph(value = "userNotificationInfo.getAllByPage", type = EntityGraph.EntityGraphType.FETCH)
    Page<UserNotificationInfo> findAll(@Nullable Specification<UserNotificationInfo> specification, Pageable pageable);
}
