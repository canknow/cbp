package com.canknow.cbp.base.application.dto;

import lombok.Data;

@Data
public abstract class Dto implements IDto {
    private static final long serialVersionUID = 4273756835290346948L;
    private String id;
}
