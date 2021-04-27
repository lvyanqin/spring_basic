package com.lynn.mybaits.v2;

import com.lynn.mybaits.v2.mapper.BlogMapper;
import com.lynn.mybaits.v2.session.DefaultSqlSession;
import com.lynn.mybaits.v2.session.SqlSessionFacotry;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public class Test {

    public static void main(String[] args) {
        SqlSessionFacotry sqlSessionFacotry = new SqlSessionFacotry().build();
        DefaultSqlSession sqlSession = sqlSessionFacotry.openSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
    }

}
