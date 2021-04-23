package com.lynn.spring.webmvc.servlet;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
@Data
@AllArgsConstructor
public class MyHandlerMapping {

    private Pattern url;
    private Method method;
    private Object target;

}
