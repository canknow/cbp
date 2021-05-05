package com.canknow.cbp.common.domain.user;

import com.canknow.cbp.base.domain.entities.audit.FullAuditedEntityBase;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public abstract class UserAccount extends FullAuditedEntityBase {
    private static final long serialVersionUID = -5324647244771811431L;

    private String userId;

    private String userLinkId;

    private String userName;
}
