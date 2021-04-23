package com.lynn.spring.webmvc.servlet;

import lombok.Data;

import java.util.Map;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
@Data
public class MyModelAndView {

    private Map<String, Object> model;
    private String viewName;

    public MyModelAndView(Map<String, Object> model, String viewName) {
        this.model = model;
        this.viewName = viewName;
    }

    public MyModelAndView(String viewName) {
        this.viewName = viewName;
    }
}
