package com.canknow.cbp.base.domain.entities;

import org.hibernate.annotations.Where;

@Where(clause = "is_deleted = 0")
public interface ISoftDelete {
    boolean isDeleted();

    void setDeleted(boolean isDeleted);
}
