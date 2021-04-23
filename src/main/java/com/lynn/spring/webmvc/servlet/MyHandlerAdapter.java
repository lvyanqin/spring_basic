package com.lynn.spring.webmvc.servlet;

import com.lynn.spring.annotation.MyRequestParam;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lvyanqin
 * @since 2021-04-23
 */
@Data
public class MyHandlerAdapter {


    public MyModelAndView handler(MyHandlerMapping handlerMapping, HttpServletRequest req, HttpServletResponse resp) throws InvocationTargetException, IllegalAccessException {
        Method method = handlerMapping.getMethod();
        Class<?>[] parameterTypes = method.getParameterTypes();
        Map<String, Integer> paramIndexMapping = new HashMap<>();
        Annotation[][] pa = method.getParameterAnnotations();
        for (int i = 0; i < pa.length ; i++) {
            for (Annotation a : pa[i]) {
                if(a instanceof MyRequestParam) {
                    paramIndexMapping.put(((MyRequestParam) a).value(), i);
                }
            }
        }

        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if(parameterType == HttpServletRequest.class || parameterType == HttpServletResponse.class) {
                paramIndexMapping.put(parameterType.getName(), i);
            } 
        }

        Map<String, String[]> params = req.getParameterMap();
        Object[] paramValues = new Object[parameterTypes.length];

        for (Map.Entry<String, String[]> param : params.entrySet()) {
            String value = Arrays.toString(params.get(param.getKey()))
                    .replaceAll("\\[|\\]", "")
                    .replaceAll("\\s+", "");
            if(!paramIndexMapping.containsKey(param.getKey())) {
                continue;
            }
            Integer index = paramIndexMapping.get(param.getKey());
            paramValues[index] = castStringValue(value, parameterTypes[index]);
        }
        if(paramIndexMapping.containsKey(HttpServletRequest.class.getName())) {
            paramValues[paramIndexMapping.get(HttpServletRequest.class.getName())] = req;
        }
        if(paramIndexMapping.containsKey(HttpServletResponse.class.getName())) {
            paramValues[paramIndexMapping.get(HttpServletResponse.class.getName())] = resp;
        }
        Object result = method.invoke(handlerMapping.getTarget(), paramValues);
        if(result == null || result instanceof Void) {
            return null;
        }
        Class<?> returnType = method.getReturnType();
        if(returnType == MyModelAndView.class) {
            return (MyModelAndView)result;
        }
        return null;
    }

    private Object castStringValue(String value, Class<?> paramType) {
        if(String.class == paramType) {
            return String.valueOf(value);
        } else if(Integer.class == paramType) {
            return Integer.parseInt(value);
        } else if(Double.class == paramType) {
            return Double.parseDouble(value);
        } else {
            if(value != null) {
                return value;
            } else {
                return null;
            }
        }
    }


}
