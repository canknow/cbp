package com.canknow.cbp.common.adapter.outbound.persistent.auditLog;

import com.canknow.cbp.jpa.repositories.IJpaRepositoryBase;
import com.canknow.cbp.common.domain.auditLog.AuditLog;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuditLogJpaRepository extends IJpaRepositoryBase<AuditLog> {
}
