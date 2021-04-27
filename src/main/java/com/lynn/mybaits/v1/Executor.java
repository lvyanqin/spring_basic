package com.lynn.mybaits.v1;

import com.lynn.mybaits.v1.mapper.Blog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public class Executor {

    public <T> List<T> query(String sql, Object... args) {
        Connection conn = null;
        Statement stmt = null;
        List<Blog> blogs = new ArrayList<Blog>();
        try {
            Class.forName("com.sql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lynn", "root", "123456");
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(String.format(sql, args));
            while (rs.next()) {
                Integer bid = rs.getInt("bid");
                String name = rs.getString("name");
                Integer authorId = rs.getInt("authorId");
                Blog blog = new Blog();
                blog.setBid(bid);
                blog.setName(name);
                blog.setAuthorId(authorId);
                blogs.add(blog);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return (List<T>) blogs;
    }

}
