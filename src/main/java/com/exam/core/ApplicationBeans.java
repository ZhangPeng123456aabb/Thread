package com.exam.core;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * @author dinkfamily
 * @date 2019/4/14 18:49
 * @description:
 */
@Component
public class ApplicationBeans implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(ApplicationBeans.applicationContext == null) {
            ApplicationBeans.applicationContext = applicationContext;
        }
    }

    /**
     * 找出所有具有指定注解（或注解的子类）的beans
     * @param clazz
     * @return
     */
    public static Collection<Object> byAnnotation(Class <? extends Annotation> clazz) {
        return applicationContext.getBeansWithAnnotation(clazz).values();
    }

    /**
     * 找出是特定class类型的bean
     * @param clazz
     * @param <T>
     * @return
     */
    public static  <T> T byClass(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }

    /**
     * 找出指定class类型和指定名称的bean
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T byNameAndClass(String name, Class<T> clazz){
        return applicationContext.getBean(name, clazz);
    }
}
