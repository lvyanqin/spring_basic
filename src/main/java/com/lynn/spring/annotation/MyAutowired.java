package com.lynn.spring.annotation;

import java.lang.annotation.*;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAutowired {

    String value() default "";

}
