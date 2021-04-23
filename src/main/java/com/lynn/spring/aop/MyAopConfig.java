package com.lynn.spring.aop;

import lombok.Data;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
@Data
public class MyAopConfig {

    private String pointCut;
    private String aspectClass;
    private String aspectBefore;
    private String aspectAfter;
    private String aspectAfterThrow;
    private String aspectAfterThrowingName;

}
