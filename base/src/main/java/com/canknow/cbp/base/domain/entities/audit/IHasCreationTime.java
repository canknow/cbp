package com.canknow.cbp.base.domain.entities.audit;

import java.time.LocalDateTime;

public interface IHasCreationTime {
    LocalDateTime getCreationTime();

    void setCreationTime(LocalDateTime value);
}
