package com.canknow.cbp.common.domain.settings;

import com.canknow.cbp.base.domain.entities.IIHasUserId;
import com.canknow.cbp.base.domain.entities.audit.FullAuditedAggregateRoot;
import com.canknow.cbp.base.settings.SettingInfo;
import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Setting extends FullAuditedAggregateRoot implements IIHasUserId {
    private String name;

    private String value;

    private String userId;

    public SettingInfo toSettingInfo() {
        return new SettingInfo(name, value, userId);
    }
}
