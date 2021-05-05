package com.canknow.cbp.common.domain.notice;

import com.canknow.cbp.base.domain.entities.audit.FullAuditedAggregateRoot;
import lombok.Data;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Entity
public class Notice extends FullAuditedAggregateRoot {

    private static final long serialVersionUID = 2216805230952257555L;
    private String title;

    private String content;

    private NoticeStatus status;

    private Integer isTop;

    private LocalDateTime publishTime;
}
