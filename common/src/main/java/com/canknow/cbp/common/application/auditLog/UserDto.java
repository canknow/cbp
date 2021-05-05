package com.canknow.cbp.common.application.auditLog;

import com.canknow.cbp.base.application.dto.Dto;
import lombok.Data;

@Data
public class UserDto extends Dto {
    private String userName;

    private String nickName;

    private String fullName;
}
