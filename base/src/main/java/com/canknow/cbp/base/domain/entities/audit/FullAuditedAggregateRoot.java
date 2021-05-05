package com.canknow.cbp.base.domain.entities.audit;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
public abstract class FullAuditedAggregateRoot extends AuditedAggregateRoot implements IFullAudited {
    private static final long serialVersionUID = -5367479973866582105L;
    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    private boolean isDeleted = false;

    @Column(length = 64)
    private String deleterUserId;

    @Column
    private LocalDateTime deletionTime;
}
