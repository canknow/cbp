package com.canknow.cbp.common.domain.user;

import com.canknow.cbp.base.domain.entities.audit.CreationAuditedAggregateRoot;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
public class UserInvitation extends CreationAuditedAggregateRoot {
    private String number;

    @Column(length = 64)
    private String userId;
}
