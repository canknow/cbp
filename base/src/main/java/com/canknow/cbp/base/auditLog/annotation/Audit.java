package com.canknow.cbp.base.auditLog.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * 记录：日志名称，日志类型，日志备注
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Audit {
    /**
     * 日志名称
     */
    String name() default "";

    /**
     * 日志名称
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 日志备注
     */
    String remark() default "";
}
