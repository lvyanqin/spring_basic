package com.lynn.mybaits.v2.executor;

import java.util.List;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public interface Executor<T> {

     List<T> query(String sql, Class<T> targetClass, Object... args);

}
