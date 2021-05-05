package com.canknow.cbp.common.domain.feedback;

import com.canknow.cbp.base.domain.entities.IIHasUserId;
import com.canknow.cbp.base.domain.entities.audit.FullAuditedAggregateRoot;
import lombok.Data;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import java.util.List;

@Data
@Entity
public class Feedback extends FullAuditedAggregateRoot implements IIHasUserId {
    private static final long serialVersionUID = -5071928455598926105L;
    private String userId;

    private String mobile;

    private String content;

    private String source;

    private String type;

    @ElementCollection
    private List<String> files;
}
