package com.canknow.cbp.base.domain.entities;

import com.canknow.cbp.base.domain.entities.audit.FullAuditedAggregateRoot;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class NameEntity extends FullAuditedAggregateRoot {
    private String name;
}
