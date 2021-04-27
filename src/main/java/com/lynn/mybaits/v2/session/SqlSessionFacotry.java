package com.lynn.mybaits.v2.session;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public class SqlSessionFacotry {

    MyConfiguration configuration;

    public SqlSessionFacotry build() {
        configuration = new MyConfiguration();
        return this;
    }

    public DefaultSqlSession openSession() {
        return new DefaultSqlSession(configuration);
    }
}
