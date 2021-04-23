package com.lynn.spring.aop.aspect;


import com.lynn.spring.aop.intercept.MyMethodInterceptor;
import com.lynn.spring.aop.intercept.MyMethodInvocation;

import java.lang.reflect.Method;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
public class MyMethodBeforeAdviceInterceptor extends MyAbstractAspectJAdvice implements MyMethodInterceptor {

    private MyJoinPoint jp;

    public MyMethodBeforeAdviceInterceptor(Object aspect, Method method) {
        super(aspect, method);
    }

    @Override
    public Object invoke(MyMethodInvocation mi) throws Throwable {
        jp = mi;
        this.before(mi.getMethod(), mi.getArguments(), mi.getThis());
        return mi.process();
    }

    private void before(Method method, Object[] arguments, Object target) throws Exception {
        super.invokeAdviceMethod(this.jp, null, null);
    }
}
