package com.canknow.cbp.base.notification;

import com.alibaba.fastjson.JSONObject;
import com.canknow.cbp.base.domain.entities.audit.CreationAuditedAggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class NotificationInfo extends CreationAuditedAggregateRoot {
    private String notificationName;

    @Lob
    @Basic(fetch= FetchType.LAZY)
    private String data;

    private String userIds;

    public Notification toNotification() {
        return new Notification(notificationName, JSONObject.parseObject(data));
    }
}
