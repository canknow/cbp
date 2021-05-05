package com.canknow.cbp.common.adapter.outbound.persistent.auditLog;

import com.canknow.cbp.common.domain.auditLog.AuditLog;
import com.canknow.cbp.common.domain.auditLog.IAuditLogRepository;
import com.canknow.cbp.jpa.repositories.JpaRepositoryBase;
import org.springframework.stereotype.Component;

@Component
public class AuditLogRepository extends JpaRepositoryBase<AuditLog, IAuditLogJpaRepository> implements IAuditLogRepository {

    public AuditLogRepository(IAuditLogJpaRepository jpaRepository) {
        super(jpaRepository);
    }
}
