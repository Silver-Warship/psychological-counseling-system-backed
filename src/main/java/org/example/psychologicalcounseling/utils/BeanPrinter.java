package org.example.psychologicalcounseling.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Component
public class BeanPrinter implements ApplicationListener<ApplicationReadyEvent> {
    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
//        String[] beanNames = applicationContext.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//
//        for (String beanName : beanNames) {
//            Object bean = applicationContext.getBean(beanName);
//            System.out.println("Bean name: " + beanName + ", Bean class: " + bean.getClass().getName());
//        }
    }
}
