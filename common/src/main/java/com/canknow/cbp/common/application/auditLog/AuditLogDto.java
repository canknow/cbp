package com.canknow.cbp.common.application.auditLog;

import com.canknow.cbp.base.application.dto.FullAuditedDto;
import com.canknow.cbp.base.autoMapper.AutoMap;
import lombok.Data;

@Data
public class AuditLogDto extends FullAuditedDto {
    private String requestId;

    private String module;

    private String controllerName;

    private String actionName;

    private String remark;

    private Integer type;

    private boolean isIgnore;

    private String userId;

    private String userName;

    private String ip;

    private String area;

    private String operator;

    private String requestUrl;

    private String requestMethod;

    private String contentType;

    private Boolean requestBody;

    private String param;

    private String token;

    private boolean isSuccess = true;

    private Integer code;

    private String message;

    private String exceptionName;

    private String exceptionMessage;

    private String browserName;

    private String browserVersion;

    private String engineName;

    private String engineVersion;

    private String osName;

    private String platformName;

    private String deviceName;

    private String deviceModel;

    @AutoMap(type = UserDto.class)
    private UserDto creatorUser;
}
