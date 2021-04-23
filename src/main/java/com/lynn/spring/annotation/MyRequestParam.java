package com.lynn.spring.annotation;

import java.lang.annotation.*;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyRequestParam {

    String value() default "";

    boolean required() default true;

}
