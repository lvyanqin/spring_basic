package com.lynn.mybaits.v2.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lvyanqin
 * @since 2021-04-27
 */
public class Plugin implements InvocationHandler {

    Interceptor interceptor;

    public Plugin(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        interceptor.interceptor();
        return method.invoke(proxy, args);
    }

}
