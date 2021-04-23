package com.lynn.spring.aop.aspect;


import com.lynn.spring.aop.intercept.MyMethodInterceptor;
import com.lynn.spring.aop.intercept.MyMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
public class MyMethodAfterThrowAdviceInterceptor extends MyAbstractAspectJAdvice implements MyMethodInterceptor {

    private MyJoinPoint jp;

    public MyMethodAfterThrowAdviceInterceptor(Object aspect, Method method) {
        super(aspect, method);
    }

    @Override
    public Object invoke(MyMethodInvocation mi) throws Throwable {
        jp = mi;
        try {
            return mi.process();
        } catch (Throwable e) {
            this.afterThrow(e.getCause(), mi.getMethod(), mi.getArguments(), mi.getThis());
            throw e;
        }
    }

    private Object afterThrow(Throwable t, Method method, Object[] arguments, Object target) throws Exception {
        return super.invokeAdviceMethod(this.jp, null, t);
    }
}
