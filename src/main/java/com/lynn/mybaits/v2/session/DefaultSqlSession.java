package com.lynn.mybaits.v2.session;

import com.lynn.mybaits.v2.executor.Executor;

import java.util.List;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public class DefaultSqlSession {

    private MyConfiguration configuration;

    private Executor executor;

    public DefaultSqlSession(MyConfiguration configuration) {
        this.configuration = configuration;
        executor = configuration.newExecutor();
    }

    public <T> T getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz, this);
    }

    public <T> List<T> query(String statementId, Class<T> targetClass, Object... args) {
        String sql = MyConfiguration.mapperStatements.get(statementId);
        return executor.query(sql, targetClass, args);
    }
}
