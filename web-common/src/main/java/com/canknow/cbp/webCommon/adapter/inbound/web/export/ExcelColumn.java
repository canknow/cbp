package com.canknow.cbp.webCommon.adapter.inbound.web.export;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
    int order() default 9999;

    String title() default "";

    int width() default 0;

    String pattern() default "";
}