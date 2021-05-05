package com.canknow.cbp.base.domain.entities.audit;

import com.canknow.cbp.base.domain.entities.ISoftDelete;

import java.time.LocalDateTime;

public interface IHasDeletionTime extends ISoftDelete {
    LocalDateTime getDeletionTime();

    void setDeletionTime(LocalDateTime value);
}
