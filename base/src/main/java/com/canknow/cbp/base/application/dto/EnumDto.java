package com.canknow.cbp.base.application.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class EnumDto<T> {
    private Integer code;

    private String name;

    private String description;

    private T baseEnum;
}
