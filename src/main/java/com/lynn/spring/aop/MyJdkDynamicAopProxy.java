package com.lynn.spring.aop;

import com.lynn.spring.aop.intercept.MyMethodInvocation;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
@AllArgsConstructor
public class MyJdkDynamicAopProxy extends MyAopProxy implements InvocationHandler {

    private MyAdviseSupport advise;

    @Override
    public Object getProxy() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), this.getClass().getInterfaces(), this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        List<Object> chain = this.advise.getInterceptorsAndDynamicInterceptionAdvice(method, this.advise.getTargetClass());
        MyMethodInvocation mi = new MyMethodInvocation(proxy, this.advise.getTarget(), method, args, this.advise.getTargetClass(), chain);
        return mi.process();
    }
}
