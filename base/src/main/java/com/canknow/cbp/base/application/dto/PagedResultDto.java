package com.canknow.cbp.base.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResultDto<TDto> extends ListResultDto<TDto> {
    private static final long serialVersionUID = 602654813199637091L;
    private long totalCount;

    public PagedResultDto(List<TDto> items, long totalCount) {
        this.setItems(items);
        this.totalCount = totalCount;
    }
}
