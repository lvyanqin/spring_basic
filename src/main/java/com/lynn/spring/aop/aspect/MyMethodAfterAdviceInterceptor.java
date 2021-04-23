package com.lynn.spring.aop.aspect;


import com.lynn.spring.aop.intercept.MyMethodInterceptor;
import com.lynn.spring.aop.intercept.MyMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
public class MyMethodAfterAdviceInterceptor extends MyAbstractAspectJAdvice implements MyMethodInterceptor {

    private MyJoinPoint jp;

    public MyMethodAfterAdviceInterceptor(Object aspect, Method method) {
        super(aspect, method);
    }

    @Override
    public Object invoke(MyMethodInvocation mi) throws Throwable {
        jp = mi;
        Object returnValue = mi.process();
        return this.after(returnValue, mi.getMethod(), mi.getArguments(), mi.getThis());
    }

    private Object after(Object returnValue, Method method, Object[] arguments, Object target) throws Exception {
        return super.invokeAdviceMethod(this.jp, returnValue, null);
    }
}
