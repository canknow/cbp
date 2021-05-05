package com.canknow.cbp.base.application.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class GetAllByPageInput extends GetAllInput {
    @Min(value = 0, message = "pageSizeShouldGreatThan0")
    @Max(value = 100, message = "pageSizeShouldLessThan100")
    private int pageSize = 10;

    @Min(value = 0, message = "pageIndexShouldGreatThan0")
    private int pageIndex = 0;
}
