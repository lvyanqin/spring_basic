package com.lynn.spring.aop.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
@Data
@AllArgsConstructor
public abstract class MyAbstractAspectJAdvice {

    private Object aspect;
    private Method adviceMethod;

    protected Object invokeAdviceMethod(MyJoinPoint jp, Object returnValue, Throwable t) throws Exception {
        Class<?>[] paramTypes = adviceMethod.getParameterTypes();
        if(null == paramTypes || paramTypes.length == 0) {
            return this.adviceMethod.invoke(this.aspect);
        }
        Object[] args = new Object[paramTypes.length];
        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> paramType = paramTypes[i];
            if(paramType == MyJoinPoint.class) {
                args[i] = jp;
            } else if(paramType == Throwable.class) {
                args[i] = t;
            } else if(paramType == Object.class){
                args[i] = returnValue;
            }
        }
        return this.adviceMethod.invoke(aspect, args);
    }
}
