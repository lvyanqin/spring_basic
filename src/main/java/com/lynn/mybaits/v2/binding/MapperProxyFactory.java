package com.lynn.mybaits.v2.binding;

import com.lynn.mybaits.v2.session.DefaultSqlSession;

import java.lang.reflect.Proxy;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public class MapperProxyFactory<T> {

    private Class<T> mapperInterface;

    public MapperProxyFactory(Class<T> mapperInterface) {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance(DefaultSqlSession sqlSession, Class targetClass) {
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(),
                mapperInterface.getInterfaces(), new MapperProxy(sqlSession, targetClass));
    }
}
