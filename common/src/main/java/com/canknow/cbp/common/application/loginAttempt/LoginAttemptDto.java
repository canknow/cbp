package com.canknow.cbp.common.application.loginAttempt;

import com.canknow.cbp.common.domain.authorization.LoginResultType;
import com.canknow.cbp.base.application.dto.Dto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginAttemptDto extends Dto {
    private String userId;

    private String clientIpAddress;

    private String clientName;

    private String browserInfo;

    private LocalDateTime creationTime;

    private LoginResultType loginResultType;

    private String region;
}
