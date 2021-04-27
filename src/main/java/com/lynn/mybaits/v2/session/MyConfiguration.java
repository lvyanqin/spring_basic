package com.lynn.mybaits.v2.session;

import com.lynn.mybaits.v2.annotaion.Select;
import com.lynn.mybaits.v2.binding.MapperProxyFactory;
import com.lynn.mybaits.v2.executor.CachingExecutor;
import com.lynn.mybaits.v2.executor.Executor;
import com.lynn.mybaits.v2.executor.SimpleExecutor;
import com.lynn.mybaits.v2.plugin.Interceptor;
import com.lynn.mybaits.v2.plugin.InterceptorChain;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author lvyanqin
 * @since 2021-04-26
 */
public class MyConfiguration {

    private static final ResourceBundle sqlMapping;
    private static final ResourceBundle properties;

    // 维护接口方法 与 sql关系
    public static Map<String, String> mapperStatements = new HashMap<>();
    private Map<Class<?>, MapperProxyFactory> MAPPER_REGISTRY = new HashMap<>();
    private List<Class<?>> mapperList = new ArrayList<>();
    private List<String> classPaths = new ArrayList<>();
    private InterceptorChain interceptorChain = new InterceptorChain();

    static {
        sqlMapping = ResourceBundle.getBundle("v2sql");
        properties = ResourceBundle.getBundle("mybatis");
    }

    public MyConfiguration() {
        // 解析 statementId
        handleSqlMapping();
        // 解析 mapper
        handlerMapper();
        // 解析插件
        handlerPlugin();
    }

    private void handlerPlugin() {
        String pluginPath = properties.getString("plugin.path");
        String[] plugins = pluginPath.split(",");
        try {
            for (String plugin : plugins) {
                interceptorChain.addInterceptor((Interceptor) Class.forName(plugin).newInstance());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlerMapper() {
        String scanPath = properties.getString("mapper.path");
        scanPackage(scanPath);
        for (Class<?> mapper : mapperList) {
            parsingClass(mapper);
        }
    }

    private void parsingClass(Class<?> mapper) {
        // 解析 mapper上注解的sql
        for (Method method : mapper.getMethods()) {
            if(method.isAnnotationPresent(Select.class)) {
                String sql = method.getAnnotation(Select.class).value();
                mapperStatements.put(method.getClass().getName() + "." + method.getName(), sql);
            }
        }
    }

    private void scanPackage(String scanPath) {
        String path = this.getClass().getResource("/").getPath() + scanPath.replaceAll(".", File.separator);
        doPath(new File(path));
        transferClass();
    }

    private void doPath(File file) {
        for (File f : file.listFiles()) {
            if(f.isDirectory()) {
                doPath(f);
            } else {
                if(f.getName().endsWith(".class")) {
                    classPaths.add(f.getPath());
                }
            }
        }
    }

    private void transferClass() {
        for (String classPath : classPaths) {
            addMapperInterface(classPath);
        }
    }

    private void addMapperInterface(String classPath) {
        String className = classPath.replaceAll(this.getClass().getResource("/").getPath(), "")
                .replaceAll("/", ".")
                .replaceAll(".class", "");
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(clazz.isInterface()) {
            mapperList.add(clazz);
            MAPPER_REGISTRY.put(clazz, new MapperProxyFactory(clazz));
        }
    }

    private void handleSqlMapping() {
        for (String s : sqlMapping.keySet()) {
            // statementId
            String[] values = sqlMapping.getString(s).split("--");
            String sql = values[0];
            String returnTypeName = values[1];
            mapperStatements.put(s, sql);

        }
    }

    public Executor newExecutor() {
        String cache = properties.getString("cache.enable");
        Executor executor = null;
        if("true".equals(cache)) {
            executor = new CachingExecutor(new SimpleExecutor());
        } else {
            executor = new SimpleExecutor();
        }
        return interceptorChain.pluginAll(executor);
    }

    public <T> T getMapper(Class<T> clazz, DefaultSqlSession sqlSession) {
        MapperProxyFactory proxyFactory = MAPPER_REGISTRY.get(clazz);
        return (T) proxyFactory.newInstance(sqlSession, clazz);
    }
}
