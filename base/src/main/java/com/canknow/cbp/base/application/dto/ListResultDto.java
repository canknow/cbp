package com.canknow.cbp.base.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListResultDto<TDto> implements Serializable {
    private static final long serialVersionUID = 6251306018736488980L;
    private List<TDto> items;
}
