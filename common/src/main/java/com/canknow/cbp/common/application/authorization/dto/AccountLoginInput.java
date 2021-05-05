package com.canknow.cbp.common.application.authorization.dto;

import lombok.Data;

@Data
public class AccountLoginInput {
    private String userName;
    private String password;
}
