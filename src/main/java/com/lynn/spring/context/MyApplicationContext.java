package com.lynn.spring.context;

import com.lynn.spring.annotation.MyAutowired;
import com.lynn.spring.annotation.MyController;
import com.lynn.spring.annotation.MyService;
import com.lynn.spring.aop.MyAdviseSupport;
import com.lynn.spring.aop.MyAopConfig;
import com.lynn.spring.aop.MyDefaultAopProxyFactory;
import com.lynn.spring.beans.MyBeanWrapper;
import com.lynn.spring.beans.config.MyBeanDefinition;
import com.lynn.spring.beans.support.MyBeanDefinitionReader;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author lvyanqin
 * @since 2021-04-22
 */
public class MyApplicationContext {

    MyBeanDefinitionReader reader;


    public Map<String, MyBeanDefinition> beanDefinitionMap = new HashMap<>();
    private Map<String, Object> factoryBeanObjectCache = new HashMap<>();
    private Map<String, MyBeanWrapper> factoryBeanInstanceCache = new HashMap<>();

    MyDefaultAopProxyFactory proxyFactory = new MyDefaultAopProxyFactory();

    public MyApplicationContext(String... configLocations) {
        reader = new MyBeanDefinitionReader(configLocations[0]);
        List<MyBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        doRegistryBeanDefinition(beanDefinitions);
        doAutowired();

    }

    private void doAutowired() {
        for (Map.Entry<String, MyBeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            getBean(beanName);
        }
    }

    public Object getBean(String beanName) {
        MyBeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        // 反射实例化newInstance
        Object instance = initializingBean(beanName, beanDefinition);
        MyBeanWrapper beanWrapper = new MyBeanWrapper(instance);
        // 缓存
        factoryBeanInstanceCache.put(beanName, beanWrapper);
        // 依赖注入
        populateBean(beanName, beanDefinition, beanWrapper);

        return beanWrapper.getWrapperInstance();
            

    }

    private void populateBean(String beanName, MyBeanDefinition beanDefinition, MyBeanWrapper beanWrapper) {
        Class<?> wrapperClass = beanWrapper.getWrapperClass();
        Object instance = beanWrapper.getWrapperInstance();
        if(!(wrapperClass.isAnnotationPresent(MyController.class) || wrapperClass.isAnnotationPresent(MyService.class))) {
            return ;
        }
        for (Field filed : wrapperClass.getDeclaredFields()) {
            if(!filed.isAnnotationPresent(MyAutowired.class)) {
                continue;
            } else {
                MyAutowired autowired = filed.getAnnotation(MyAutowired.class);
                String autowiredBeanName = autowired.value().trim();
                if("".equals(autowiredBeanName)) {
                    autowiredBeanName = filed.getType().getName();
                }
                filed.setAccessible(true);
                if(!this.factoryBeanInstanceCache.containsKey(autowiredBeanName)) {
                    continue;
                }
                try {
                    filed.set(instance, this.factoryBeanInstanceCache.get(autowiredBeanName).getWrapperInstance());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private Object initializingBean(String beanName, MyBeanDefinition beanDefinition) {
        try {
            Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
            Object instance = clazz.newInstance();
            // AOP
            MyAdviseSupport config = instantionAopConfig();
            config.setTargetClass(clazz);
            config.setTarget(clazz);
            if(config.pointCutMatch()) {
                instance = proxyFactory.createAopProxy(config).getProxy();
            }

            factoryBeanObjectCache.put(beanName, instance);
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private MyAdviseSupport instantionAopConfig() {
        MyAopConfig config = new MyAopConfig();
        config.setPointCut(this.reader.getConfig().getProperty("pointCut"));
        config.setAspectClass(this.reader.getConfig().getProperty("aspectClass"));
        config.setAspectBefore(this.reader.getConfig().getProperty("aspectBefore"));
        config.setAspectAfter(this.reader.getConfig().getProperty("aspectAfter"));
        config.setAspectAfterThrow(this.reader.getConfig().getProperty("aspectAfterThrow"));
        config.setAspectAfterThrowingName(this.reader.getConfig().getProperty("aspectAfterThrowingName"));
        return new MyAdviseSupport(config);
    }

    private void doRegistryBeanDefinition(List<MyBeanDefinition> beanDefinitions) {
        for (MyBeanDefinition beanDefinition : beanDefinitions) {
            beanDefinitionMap.put(beanDefinition.getFactoryBeanName(), beanDefinition);
//            beanDefinitionMap.put(beanDefinition.getBeanClassName(), beanDefinition);
        }
    }

    public String[] getBeanDefinitionNames() {
        return this.beanDefinitionMap.keySet().toArray(new String[this.beanDefinitionMap.size()]);
    }

    public int getBeanDefinitionCount() {
        return this.beanDefinitionMap.size();
    }

    public Properties getConfig() {
        return this.reader.getConfig();
    }

}
