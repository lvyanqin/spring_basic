package com.lynn.mybaits.v1;

import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
@AllArgsConstructor
public class MapperProxyFactory implements InvocationHandler {

    DefaultSqlSession sqlSession;


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String stamentId = method.getClass().getName() + "." + method.getName();
        return sqlSession.query(stamentId, args);
    }
}
