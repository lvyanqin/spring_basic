package com.lynn.spring.webmvc.servlet;

import com.lynn.spring.annotation.MyController;
import com.lynn.spring.annotation.MyRequestMapping;
import com.lynn.spring.context.MyApplicationContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author lvyanqin
 * @since 2021-04-22
 */
public class MyDispatchServlet extends HttpServlet {

    MyApplicationContext applicationContext ;
    List<MyHandlerMapping> handlerMappings = new ArrayList<>();
    Map<MyHandlerMapping, MyHandlerAdapter> handlerAdapters = new HashMap<>();
    private List<MyViewResolver> viewResolvers = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        applicationContext = new MyApplicationContext(config.getInitParameter("contextConfigLocation"));
        initStrategies(applicationContext);
    }

    private void doDispacher(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        MyHandlerMapping handler = getHandler(req);
        if(handler == null) {
            processDispatchResult(req, resp, new MyModelAndView("404"));
            return ;
        }
        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        MyModelAndView mv = adapter.handler(handler, req, resp);
        processDispatchResult(req, resp, mv);
    }

    private void processDispatchResult(HttpServletRequest req, HttpServletResponse resp, MyModelAndView mv) throws IOException {
        if(mv == null) {
            return;
        }
        if(this.viewResolvers.isEmpty()) {
            return ;
        }
        for (MyViewResolver viewResolver : viewResolvers) {
            MyView view = viewResolver.resolveViewName(mv.getViewName());
            view.render(mv.getModel(), req, resp);
        }


    }

    private MyHandlerAdapter getHandlerAdapter(MyHandlerMapping handler) {
        return handlerAdapters.get(handler);
    }

    private MyHandlerMapping getHandler(HttpServletRequest req) {
        String url = req.getRequestURI();
        String contextPath = req.getContextPath();
        url = url.replaceAll(contextPath, "").replaceAll("/+", "/");
        for (MyHandlerMapping handlerMapping : handlerMappings) {
            if(handlerMapping.getUrl().matcher(url).matches()) {
                return handlerMapping;
            }
        }
        return null;
    }


    private void initStrategies(MyApplicationContext context) {
        // 初始化 handlerMapping
        initHandlerMapping(context);
        // 参数适配器
        initHandlerAdapter(context);
        // 初始化 viewResolver
        initViewResolvers(context);
    }

    private void initViewResolvers(MyApplicationContext context) {
        String templateRoot = context.getConfig().getProperty("templateRoot");
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        File templateRootDir = new File(templateRootPath);
        this.viewResolvers.add(new MyViewResolver(templateRootDir));

    }

    private void initHandlerAdapter(MyApplicationContext context) {
        for (MyHandlerMapping handlerMapping : handlerMappings) {
            handlerAdapters.put(handlerMapping, new MyHandlerAdapter());
        }
    }

    private void initHandlerMapping(MyApplicationContext context) {
        if(context.getBeanDefinitionCount() == 0) {
            return ;
        }
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            Object instance = context.getBean(beanDefinitionName);
            Class<?> clazz = instance.getClass();
            if(!clazz.isAnnotationPresent(MyController.class)) {
                continue;
            }
            String baseUrl = "";
            if(clazz.isAnnotationPresent(MyRequestMapping.class)) {
                baseUrl += clazz.getAnnotation(MyRequestMapping.class).value();
            }
            for (Method m : clazz.getDeclaredMethods()) {
                if(!m.isAnnotationPresent(MyRequestMapping.class)) {
                    continue;
                }
                String regex = ("/" + baseUrl + "/" + m.getAnnotation(MyRequestMapping.class).value().replaceAll("\\*", ".*"))
                        .replaceAll("/+", "/");
                Pattern pattern = Pattern.compile(regex);
                handlerMappings.add(new MyHandlerMapping(pattern, m, instance));
            }

        }

    }
}
