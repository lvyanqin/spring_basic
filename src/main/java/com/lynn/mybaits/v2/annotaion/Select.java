package com.lynn.mybaits.v2.annotaion;

import java.lang.annotation.*;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Select {

    String value();

}
