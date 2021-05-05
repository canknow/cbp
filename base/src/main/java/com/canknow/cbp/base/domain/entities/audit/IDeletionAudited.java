package com.canknow.cbp.base.domain.entities.audit;

public interface IDeletionAudited extends IHasDeletionTime {
    String getDeleterUserId();

    void setDeleterUserId(String deleterUserId);
}
