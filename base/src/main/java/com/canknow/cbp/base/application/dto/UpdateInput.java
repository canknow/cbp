package com.canknow.cbp.base.application.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateInput extends CreateInput implements IDto {
    private static final long serialVersionUID = 8402127256796813158L;

    @NotEmpty(message = "idCannotBeNull")
    private String id;
}
