package com.canknow.cbp.base.domain.entities.audit;

import lombok.Data;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditedAggregateRoot extends CreationAuditedAggregateRoot implements IAudited {
    private static final long serialVersionUID = -7012641172335919057L;
    @Column
    @LastModifiedDate
    private LocalDateTime lastModificationTime;

    @Column(length = 64)
    @LastModifiedBy
    private String lastModifierUserId;
}
