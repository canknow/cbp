package com.canknow.cbp.common.domain.auditLog;

import com.canknow.cbp.base.domain.entities.IIHasUserId;
import com.canknow.cbp.base.domain.entities.audit.CreationAuditedEntityBase;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Data
@Accessors(chain = true)
public class AuditLog extends CreationAuditedEntityBase implements IIHasUserId {
    private static final long serialVersionUID = 4665747834208458633L;

    private String userId;

    private String requestId;

    private String module;

    private String controllerName;

    private String actionName;

    private String remark;

    private Integer type;

    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    private boolean isIgnore;

    private String userName;

    private String ip;

    private String area;

    private String operator;

    @Column(length = 1024)
    private String requestUrl;

    private String requestMethod;

    private String contentType;

    @Column(nullable = false, columnDefinition = "bit(1) default 0")
    private boolean requestBody;

    @Lob
    @Basic(fetch=FetchType.LAZY)
    private String param;

    @Column(length = 1024)
    private String token;

    @Column(nullable = false, columnDefinition = "bit(1) default 1")
    private boolean isSuccess = true;

    private Integer code;

    @Lob
    @Basic(fetch=FetchType.LAZY)
    private String message;

    private String exceptionName;

    @Lob
    @Basic(fetch=FetchType.LAZY)
    private String exceptionMessage;

    private String browserName;

    private String browserVersion;

    private String engineName;

    private String engineVersion;

    private String osName;

    private String platformName;

    private String deviceName;

    private String deviceModel;

    private Long duration;

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
