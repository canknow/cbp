package com.canknow.cbp.common.domain.authorization;

import com.canknow.cbp.base.domain.entities.audit.CreationAuditedAggregateRoot;
import com.canknow.cbp.common.domain.role.CbpRole;
import com.canknow.cbp.common.domain.user.CbpUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
public abstract class CbpLoginAttempt<TUser extends CbpUser<TUser, TRole>, TRole extends CbpRole<TUser, TRole>> extends CreationAuditedAggregateRoot {
    private static final long serialVersionUID = 8717162701587613331L;
    @Column(length = 64)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false, nullable = true)
    private TUser user;

    private String clientIpAddress;

    private String clientName;

    private String browserInfo;

    @Enumerated(EnumType.ORDINAL)
    private LoginResultType loginResultType;

    private String region;
}
