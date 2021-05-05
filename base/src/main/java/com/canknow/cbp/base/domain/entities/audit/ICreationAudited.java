package com.canknow.cbp.base.domain.entities.audit;

public interface ICreationAudited extends IHasCreationTime {
    String getCreatorUserId();

    void setCreatorUserId(String value);
}
