package com.canknow.cbp.common.application.auditLog;

import com.canknow.cbp.base.application.dto.GetAllByPageInput;
import lombok.Data;

@Data
public class AuditLogGetAllInput extends GetAllByPageInput {
    private String userId;

    private String userName;

    private String remark;

    private Integer type;

    private String module;

    private String controllerName;

    private String actionName;

    private String ip;

    private String area;

    private String operator;

    private String requestUrl;

    private Integer code;

    private String message;

    private String exceptionName;

    private String browserName;

    private String browserVersion;

    private String engineName;

    private String engineVersion;

    private String osName;

    private String platformName;

    private String deviceName;

    private String deviceModel;

    private String requestMethod;

    private String contentType;
}
