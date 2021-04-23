package com.lynn.spring.aop;

import com.lynn.spring.aop.aspect.MyMethodAfterAdviceInterceptor;
import com.lynn.spring.aop.aspect.MyMethodAfterThrowAdviceInterceptor;
import com.lynn.spring.aop.aspect.MyMethodBeforeAdviceInterceptor;
import lombok.Data;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
@Getter
public class MyAdviseSupport {

    private MyAopConfig config;
    private Object target;
    private Class<?> targetClass;
    private Pattern pointCutClassPattern;

    private Map<Method, List<Object>> methodCache;


    public MyAdviseSupport(MyAopConfig config) {
        this.config = config;
    }


    public void setTarget(Object target) {
        this.target = target;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public void parse() {
        String pointCut = config.getPointCut()
                .replaceAll("\\.", "\\\\.")
                .replaceAll("\\\\.\\*", ".*")
                .replaceAll("\\(", "\\\\(")
                .replaceAll("\\)", "\\\\)");
        String pointCutForClassRegex = pointCut.substring(0, pointCut.lastIndexOf("\\(") - 4);
        pointCutClassPattern = Pattern.compile("class" + pointCutForClassRegex.substring(pointCutForClassRegex.lastIndexOf(" ") + 1));

        methodCache = new HashMap<>();
        Pattern pointCutPattern = Pattern.compile(pointCut);
        try {
            Class<?> aspectClass = Class.forName(this.getConfig().getAspectClass());
            Map<String, Method> aspectMethods = new HashMap<>();
            for (Method method : aspectClass.getMethods()) {
                aspectMethods.put(method.getName(), method);
            }

            for (Method method : this.getTargetClass().getMethods()) {
                String methodString = method.toString();
                if(methodString.contains("thorws")) {
                    methodString = methodString.substring(0, methodString.lastIndexOf("throws")).trim();
                }
                Matcher matcher = pointCutPattern.matcher(methodString);
                if(matcher.matches()) {
                    List<Object> advices = new LinkedList<>();
                    if(null != config.getAspectBefore() && !"".equals(config.getAspectBefore())) {
                        advices.add(new MyMethodBeforeAdviceInterceptor(aspectClass.newInstance(), aspectMethods.get(config.getAspectBefore())));
                    }
                    if(null != config.getAspectAfter() && !"".equals(config.getAspectAfter())) {
                        advices.add(new MyMethodAfterAdviceInterceptor(aspectClass.newInstance(), aspectMethods.get(config.getAspectAfter())));
                    }
                    if(null != config.getAspectAfterThrow() && !"".equals(config.getAspectAfterThrow())) {
                        advices.add(new MyMethodAfterThrowAdviceInterceptor(aspectClass.newInstance(), aspectMethods.get(config.getAspectAfterThrow())));
                    }

                    methodCache.put(method, advices);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Boolean pointCutMatch() {
        return pointCutClassPattern.matcher(this.targetClass.toString()).matches();
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) throws NoSuchMethodException {
        List<Object> cached = this.methodCache.get(method);
        if(cached == null) {
            Method m = targetClass.getMethod(method.getName(), method.getParameterTypes());
            cached = this.methodCache.get(m);
            this.methodCache.put(m, cached);
        }
        return cached;
    }
}
