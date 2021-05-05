package com.canknow.cbp.common.application.file;

import com.canknow.cbp.base.application.dto.Dto;
import lombok.Data;

@Data
public class UserDto extends Dto {
    private String userName;

    private String avatar;

    private String phoneNumber;

    private String emailAddress;

    private String fullName;
}
