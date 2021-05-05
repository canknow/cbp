package com.canknow.cbp.web.adapter.inbound.web.api.settings;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateSettingsInput {
    private List<UpdateSettingDto> items = new ArrayList<>();
}
