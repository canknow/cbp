package com.canknow.cbp.common.domain.news;

import com.canknow.cbp.base.domain.entities.audit.FullAuditedAggregateRoot;
import lombok.Data;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@Entity
public class News extends FullAuditedAggregateRoot {
    private static final long serialVersionUID = -8208895257823929158L;

    private String newsTitle;

    private LocalDateTime publishTime;

    private String author;

    private String editor;

    private String tags;

    private String keyWords;

    private String content;

    @Column(columnDefinition = "int default 0")
    private Integer readingCount;

    private String status;
}
