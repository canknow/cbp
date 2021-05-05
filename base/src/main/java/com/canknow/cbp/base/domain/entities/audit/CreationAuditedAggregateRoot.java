package com.canknow.cbp.base.domain.entities.audit;

import com.canknow.cbp.base.domain.entities.AggregateRoot;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class CreationAuditedAggregateRoot extends AggregateRoot implements ICreationAudited {
    private static final long serialVersionUID = 9061292072118901896L;
    @CreatedBy
    @Column(length = 64)
    private String creatorUserId;

    @CreatedDate
    @Column()
    private LocalDateTime creationTime;
}
