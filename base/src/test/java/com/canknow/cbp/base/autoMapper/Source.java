package com.canknow.cbp.base.autoMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Source {
    private boolean isActive;

    private Boolean isHot;

    private String name;
}