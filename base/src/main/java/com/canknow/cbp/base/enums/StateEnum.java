package com.canknow.cbp.base.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StateEnum implements BaseEnum {
    DISABLE(0, "off", "off"),
    ENABLE(1, "on", "on");

    private Integer code;
    private String name;
    private String description;
}
