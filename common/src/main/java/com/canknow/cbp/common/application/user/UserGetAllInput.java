package com.canknow.cbp.common.application.user;

import com.canknow.cbp.base.application.dto.GetAllByPageInput;
import lombok.Data;

@Data
public class UserGetAllInput extends GetAllByPageInput {
    private String userName;

    private String fullName;

    private String phoneNumber;
}
