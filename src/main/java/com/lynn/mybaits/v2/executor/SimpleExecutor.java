package com.lynn.mybaits.v2.executor;

import java.sql.*;
import java.util.List;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public class SimpleExecutor<T> implements Executor<T>{

    ResultSetHandler resultSetHandler = new ResultSetHandler();

    @Override
    public List<T> query(String sql, Class<T> targetClass, Object... args) {
        Connection conn = null;
        PreparedStatement pstm = null;
        try {
            conn = getConnection();
            pstm = conn.prepareStatement(sql);
            ParameterHandler parameterHandler = new ParameterHandler(pstm);
            parameterHandler.setParams(args);
            pstm.execute();
            ResultSet rs = pstm.getResultSet();
            return resultSetHandler.handleResultSet(rs, targetClass);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(pstm != null) {
                try {
                    pstm.close();
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
        return null;
    }

    private Connection getConnection() throws Exception {
        Class.forName("com.sql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lynn", "root", "123456");
        return conn;
    }

}
