package com.lynn.mybaits.v2.executor;

import cn.hutool.core.collection.CollectionUtil;
import com.lynn.mybaits.v2.cache.CacheKey;

import java.util.*;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public class CachingExecutor<T> implements Executor<T>{

    Executor delegate;

    private Map<Integer, List<T>> caches = new HashMap<>();

    public CachingExecutor(Executor delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<T> query(String sql, Class<T> targetClass, Object... args) {
        CacheKey cacheKey = new CacheKey();
        cacheKey.update(sql);
        cacheKey.update(joinStr(args));

        if(caches.containsKey(cacheKey.getHashCode())) {
            return caches.get(cacheKey.getHashCode());
        } else {
            List list = delegate.query(sql, targetClass, args);
            caches.put(cacheKey.getHashCode(), list);
            return list;
        }
    }

    private String joinStr(Object... args) {
        List<Object> obs = Arrays.asList(args);
        return CollectionUtil.join(obs, ",");
    }
}
