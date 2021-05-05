package com.canknow.cbp.base.domain.entities.audit;

public interface IModificationAudited extends IHasModificationTime {
    String getLastModifierUserId();
    void setLastModifierUserId(String value);
}
