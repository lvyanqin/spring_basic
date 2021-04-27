package com.lynn.mybaits.v1;

import java.util.ResourceBundle;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public class MyConfiguration {

    public static final ResourceBundle sqlMapping ;

    static {
        sqlMapping = ResourceBundle.getBundle("v1sql");
    }

}
