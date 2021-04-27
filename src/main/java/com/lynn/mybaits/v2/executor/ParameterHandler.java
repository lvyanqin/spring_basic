package com.lynn.mybaits.v2.executor;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author lvyanqin
 * @since 2021-04-27
 */
public class ParameterHandler {

    PreparedStatement pstm;

    public ParameterHandler(PreparedStatement preparedStatement) {
        this.pstm = preparedStatement;
    }

    public void setParams(Object... args) {
        try {
            for (int i = 0; i < args.length; i++) {
                if(args[i] instanceof Integer) {
                    pstm.setInt(i + 1, (Integer) args[i]);
                } else if(args[i] instanceof Double) {
                    pstm.setDouble(i + 1, (Double) args[i]);
                } else if(args[i] instanceof Long) {
                    pstm.setLong(i + 1, (Long) args[i]);
                } else if(args[i] instanceof Date) {
                    pstm.setDate(i + 1, (Date) args[i]);
                } else {
                    pstm.setString(i + 1, (String) args[i]);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
