package com.canknow.cbp.base.application.dto;

import com.canknow.cbp.base.domain.entities.audit.IFullAudited;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FullAuditedDto extends AuditedEntityDto implements IFullAudited {
    private static final long serialVersionUID = -4375277061389679322L;
    private LocalDateTime deletionTime;

    private String deleterUserId;

    private boolean  isDeleted;
}
