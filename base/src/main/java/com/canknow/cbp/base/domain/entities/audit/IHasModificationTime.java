package com.canknow.cbp.base.domain.entities.audit;

import java.time.LocalDateTime;

public interface IHasModificationTime {
    LocalDateTime getLastModificationTime();
    void setLastModificationTime(LocalDateTime value);
}
