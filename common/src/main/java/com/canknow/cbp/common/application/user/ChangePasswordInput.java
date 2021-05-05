package com.canknow.cbp.common.application.user;

import com.canknow.cbp.base.application.dto.Dto;
import lombok.Data;

@Data
public class ChangePasswordInput extends Dto {
    private String password;
}
