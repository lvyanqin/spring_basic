package com.lynn.mybaits.v1;

import lombok.AllArgsConstructor;

import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
@AllArgsConstructor
public class DefaultSqlSession {

    private MyConfiguration configuration;
    private Executor executor;

    public <T> List<T> query(String statementId, Object... args) {
        String sql = MyConfiguration.sqlMapping.getString(statementId);
        return executor.query(sql, args);
    }


    public Object getMapper(Class<?> clazz) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new MapperProxyFactory(this));
    }
}
