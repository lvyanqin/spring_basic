package com.lynn.spring.aop;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
public class MyDefaultAopProxyFactory {

    public MyAopProxy createAopProxy(MyAdviseSupport config) {
        Class<?> targetClass = config.getTargetClass();
        if(targetClass.getInterfaces().length > 0) {
            return new MyCglibAopProxy();
        } else {
            return new MyJdkDynamicAopProxy(config);
        }
    }


}
