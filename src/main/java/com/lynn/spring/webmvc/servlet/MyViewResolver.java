package com.lynn.spring.webmvc.servlet;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
@Data
@AllArgsConstructor
public class MyViewResolver {

    private File templateRootDir;
    private final String DEFAULT_TEMPLATE_SUFFIX = ".html";

    public MyView resolveViewName(String viewName) {
        if(null == viewName || "".equals(viewName)) {
            return null;
        }
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFIX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFIX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+", "/"));
        return new MyView(templateFile);

    }

}
