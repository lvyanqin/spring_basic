package com.lynn.spring.beans.config;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lvyanqin
 * @since 2021-04-22
 */
@Data
@AllArgsConstructor
public class MyBeanDefinition {

    String factoryBeanName;
    String beanClassName;

}
