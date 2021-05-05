package com.canknow.cbp.common.application.authorization.dto;

import lombok.Data;

@Data
public class CodeLoginInput {
    private String loginProvider;

    private String code;
}
