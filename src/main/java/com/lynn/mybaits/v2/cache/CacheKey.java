package com.lynn.mybaits.v2.cache;

import lombok.Getter;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public class CacheKey {

    //默认哈希值
    private static final int DEFAULT_HASHCODE = 17;
    // 倍数
    private static final int DEFAULT_MULTIPLIER = 37;

    @Getter
    private int hashCode;
    private int count;
    private int multiplier;

    public CacheKey() {
        hashCode = DEFAULT_HASHCODE;
        count = 0;
        multiplier = DEFAULT_MULTIPLIER;
    }

    public void update(Object object) {
        int baseHashCode = object == null ? 1 : object.hashCode();
        count ++;
        baseHashCode *= count;
        hashCode = multiplier * hashCode + baseHashCode;
    }

}
