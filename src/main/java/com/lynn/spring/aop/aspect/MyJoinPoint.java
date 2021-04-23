package com.lynn.spring.aop.aspect;

import java.lang.reflect.Method;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
public interface MyJoinPoint {

    Method getMethod();

    Object[] getArguments();

    Object getThis();

    void setUserAttribute(String key, Object value);

    Object getUserAttribute(String key);


}
