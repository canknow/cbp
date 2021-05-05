package com.canknow.cbp.web.adapter.inbound.web.api.settings;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateSettingDto {
    @NotNull
    private String name;

    private String value;
}
