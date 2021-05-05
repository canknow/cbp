package com.canknow.cbp.common.domain.region;

import com.canknow.cbp.base.domain.entities.audit.FullAuditedAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@MappedSuperclass
@Getter
@Setter
@Table(
        indexes = {
                @Index(columnList = "parentCode"),
                @Index(columnList = "level"),
        },
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"code"}),
                @UniqueConstraint(columnNames = {"name"})
        }
)
public abstract class CbpRegion<TRegion extends CbpRegion<TRegion>> extends FullAuditedAggregateRoot {
    private static final long serialVersionUID = 5623501316850507086L;

    private String name;

    private String shortName;

    private String remark;

    private String code;

    private String parentCode;

    private Double latitude;

    private Double longitude;

    private Integer level;

    private Integer sort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCode", referencedColumnName = "code", insertable = false, updatable = false)
    private TRegion parent;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parentCode", referencedColumnName = "code", insertable = false, updatable = false)
    private Set<TRegion> children = new HashSet<>();
}
