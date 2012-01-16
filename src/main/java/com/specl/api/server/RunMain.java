package com.specl.api.server;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.specl.api.annotation.ApiMethod;
import org.springframework.web.bind.annotation.RequestMapping;

public class RunMain {
    private static final Logger logger = Logger.getLogger(RunMain.class);
    private static ApplicationContext context;
    private static HashMap<String,Method> methodmap;
    public static ApplicationContext getContext(){
        return context;
    }
    public static Method getMethod(String name){
        return methodmap.get(name);
    }
    public RunMain(){
        context = getApplicationContext();
        methodmap = new HashMap<String,Method>();
        Map<String,Object> map = context.getBeansWithAnnotation(Repository.class);
        logger.info("map="+map);
        Iterator<Object> objects = map.values().iterator();
        while(objects.hasNext()){
            
            Object o = objects.next();
            logger.info("object="+o);
            String controllername = o.getClass().getAnnotation(Repository.class).value();
            Method[] method = o.getClass().getDeclaredMethods();
            for (int i = 0; i < method.length; i++) {
                
                if (method[i].isAnnotationPresent(RequestMapping.class)) {
                    
                    String methodname = method[i].getAnnotation(RequestMapping.class).value()[0];
                    logger.info(controllername+methodname+"  ["+ method[i]+"]");
                    methodmap.put(controllername+methodname, method[i]);
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        if (System.getProperty("com.sun.management.jmxremote") != null) {
            System.out.println("JMX enabled.");
        } else {
            System.out.println("JMX disabled. Please set the "
                    + "'com.sun.management.jmxremote' system property to enable JMX.");
        }
        new RunMain();
        System.out.println("Listening ...");
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return new ClassPathXmlApplicationContext("serverContext.xml");
    }
}
