package com.lynn.mybaits.v2.executor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lvyanqin
 * @since 2021-04-27
 */
public class ResultSetHandler<T> {

    public List<T> handleResultSet(ResultSet rs, Class<T> clazz) throws Exception {
        Field[] fields = clazz.getFields();
        List<T> list = new ArrayList<>();
        while (rs.next()) {
            T pojo = clazz.newInstance();
            for (Field field : fields) {
                Method method = clazz.getMethod("set" + toLowerCaseLetter(field.getName()), field.getType());
                method.invoke(pojo, getResult(rs, field));
                list.add(pojo);
            }
        }
        return list;
    }


    private Object getResult(ResultSet rs, Field field) throws Exception {
        Class<?> type = field.getType();
        String name = humpToUnderLine(field.getName());
        if(type == Integer.class || type == int.class) {
            return rs.getInt(name);
        } else if(type == Double.class || type == double.class) {
            return rs.getDouble(name);
        } else if(type == Long.class || type == long.class) {
            return rs.getDate(name);
        } else if(type == Boolean.class || type == boolean.class) {
            return rs.getDate(name);
        } else if(type == Date.class) {
            return rs.getDate(name);
        } else {
            return rs.getString(name);
        }
    }

    private String toLowerCaseLetter(String name) {
        char[] chars = name.toCharArray();
        chars[0] += 32;
        return chars.toString();
    }

    /**
     * 驼峰转下划线
     * @param str
     * @return
     */
    private String humpToUnderLine(String str) {
        StringBuffer sb = new StringBuffer();
        for (char c : str.toCharArray()) {
            if(Character.isUpperCase(c)) {
                sb.append("_").append(c);
            } else {
                sb.append(c);
            }
        }
        return sb.toString().toLowerCase();
    }


}
