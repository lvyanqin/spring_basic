package com.lynn.mybaits.v1;

import com.lynn.mybaits.v1.mapper.BlogMapper;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public class Test {

    public static void main(String[] args) {
        DefaultSqlSession sqlSession = new DefaultSqlSession(new MyConfiguration(), new Executor());
        BlogMapper mapper = (BlogMapper) sqlSession.getMapper(BlogMapper.class);
        mapper.queryOne(1);
    }

}
