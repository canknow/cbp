package com.canknow.cbp.base.domain.entities.audit;

import com.canknow.cbp.base.domain.entities.EntityBase;
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
public class CreationAuditedEntityBase extends EntityBase implements ICreationAudited {
    private static final long serialVersionUID = 5503442327089174902L;

    @CreatedBy
    @Column(length = 64)
    private String creatorUserId;

    @CreatedDate
    private LocalDateTime creationTime;
}
