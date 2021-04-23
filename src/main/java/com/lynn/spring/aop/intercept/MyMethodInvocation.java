package com.lynn.spring.aop.intercept;

import com.lynn.spring.aop.aspect.MyJoinPoint;
import lombok.Data;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
@Data
public class MyMethodInvocation implements MyJoinPoint {

    private final Object proxy;

    private final Object target;

    private final Method method;

    private Object[] arguments = new Object[0];

    private final Class<?> targetClass;

    private final List<?> interceptorsAndDynamicMethodMatchers;

    private int currentInterceptorIndex = -1;

    Map<String, Object> userAttributes = new HashMap<>();

    public MyMethodInvocation(Object proxy, Object target, Method method, Object[] arguments, Class<?> targetClass, List<?> interceptorsAndDynamicMethodMatchers) {
        this.proxy = proxy;
        this.target = target;
        this.method = method;
        this.arguments = arguments;
        this.targetClass = targetClass;
        this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
    }

    public Object process() throws Throwable {
        if(this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
            return this.method.invoke(target, arguments);
        }
        Object interceptorOrInterceptionAdvice = this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
        if(interceptorOrInterceptionAdvice instanceof MyMethodInterceptor) {
            MyMethodInterceptor interceptor = (MyMethodInterceptor) interceptorOrInterceptionAdvice;
            return interceptor.invoke(this);
        } else {
            return process();
        }

    }

    @Override
    public Object getThis() {
        return this.target;
    }

    @Override
    public void setUserAttribute(String key, Object value) {
        userAttributes.put(key, value);
    }

    @Override
    public Object getUserAttribute(String key) {
        return userAttributes.get(key);
    }
}
