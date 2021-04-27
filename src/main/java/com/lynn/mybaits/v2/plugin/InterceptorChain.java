package com.lynn.mybaits.v2.plugin;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public class InterceptorChain {

    List<Interceptor> interceptors = new ArrayList<>();

    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public <T> T pluginAll(T target) {
        for (Interceptor interceptor : interceptors) {
            target = wrap(target, interceptor);
        }
        return target;
    }

    private <T> T wrap(T target, Interceptor interceptor) {
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), new Plugin(interceptor));
    }

}
