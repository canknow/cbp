package com.canknow.cbp.base.application.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class IdInput implements IDto, Serializable {
    private static final long serialVersionUID = -5353973980674510450L;

    @NotEmpty(message = "idCannotBeNull")
    private String id;
}
