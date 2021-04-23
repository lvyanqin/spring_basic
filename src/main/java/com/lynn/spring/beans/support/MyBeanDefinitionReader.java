package com.lynn.spring.beans.support;

import com.lynn.spring.beans.config.MyBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author lvyanqin
 * @since 2021-04-22
 */
public class MyBeanDefinitionReader {

    Properties contextConfig = new Properties();
    List<String> registryBeanClasses = new ArrayList<>();

    public MyBeanDefinitionReader(String configLocation) {
        doLoadConfig(configLocation);
        doScanner(contextConfig.getProperty("scanPackage"));

    }

    private void doScanner(String scanPackage) {
        URL url = this.getClass().getResource("/" + scanPackage.replaceAll("\\.", "/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if(file.isDirectory()) {
                doScanner(scanPackage + "." + file.getName());
            } else {
                String name = file.getName();
                if(!name.endsWith(".class")) {
                    continue;
                }
                registryBeanClasses.add(scanPackage + "." + name.replaceAll(".class", ""));
            }
        }
    }

    public Properties getConfig() {
        return contextConfig;
    }

    private void doLoadConfig(String configLocation) {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(configLocation.replace("classpath:", ""));
        try {
            contextConfig.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<MyBeanDefinition> loadBeanDefinitions() {
        List<MyBeanDefinition> beanDefinitions = new ArrayList<>();
        for (String className : registryBeanClasses) {
            try {
                Class<?> clazz = Class.forName(className);
                if(clazz.isInterface()) { continue; }
                beanDefinitions.add(new MyBeanDefinition(toLowerFistCase(clazz.getSimpleName()), clazz.getName()));
                for (Class<?> i : clazz.getInterfaces()) {
                    beanDefinitions.add(new MyBeanDefinition(i.getName(), clazz.getName()));
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        return beanDefinitions;
    }

    private String toLowerFistCase(String letter) {
        char[] chars = letter.toCharArray();
        chars[0] += 32;
        return chars.toString();
    }
}
