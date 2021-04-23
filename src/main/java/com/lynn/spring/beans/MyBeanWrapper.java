package com.lynn.spring.beans;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
@Data
public class MyBeanWrapper {

    private Object wrapperInstance;
    private Class<?> wrapperClass;

    public MyBeanWrapper(Object wrapperInstance) {
        this.wrapperInstance = wrapperInstance;
        this.wrapperClass = wrapperInstance.getClass();
    }
}
