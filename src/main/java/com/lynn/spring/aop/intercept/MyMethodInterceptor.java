package com.lynn.spring.aop.intercept;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
public interface MyMethodInterceptor {

    Object invoke(MyMethodInvocation mi) throws Throwable;

}
