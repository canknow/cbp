package com.canknow.cbp.base.settings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingInfo implements Serializable {
    private static final long serialVersionUID = -879816281425316127L;

    private String name;

    private String value;

    private String userId;

    public SettingInfo(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
