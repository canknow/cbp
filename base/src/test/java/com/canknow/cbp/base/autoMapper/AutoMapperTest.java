package com.canknow.cbp.base.autoMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AutoMapperTest {
    @Test
    public void mapTo() {
        Source source = new Source(true, true, "canknow");
        Target target = new Target();
        AutoMapper.mapTo(source, target);

        Assertions.assertEquals(target.getIsHot(), true);
        Assertions.assertEquals(target.isActive(), true);
        Assertions.assertEquals(target.getName(), "canknow");
    }
}