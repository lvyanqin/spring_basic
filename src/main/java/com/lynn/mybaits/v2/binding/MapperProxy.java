package com.lynn.mybaits.v2.binding;

import com.lynn.mybaits.v2.session.DefaultSqlSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public class MapperProxy implements InvocationHandler {

    DefaultSqlSession sqlSession;
    Class<?> tartgetClass;

    public MapperProxy(DefaultSqlSession sqlSession, Class<?> tartgetClass) {
        this.sqlSession = sqlSession;
        this.tartgetClass = tartgetClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String statementId = method.getClass().getName() + "." + method.getName();
        sqlSession.query(statementId, tartgetClass, args);
        return null;
    }
}
