package com.canknow.cbp.base.dependency;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SpringIocResolver implements IIocResolver {

    @Override
    public <T> T replace(String name, Class<T> tClass, Object... args) {
        ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) SpringContextUtil.getApplicationContext();
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
        beanFactory.removeBeanDefinition(name);
        return register(name, tClass, args);
    }

    @Override
    public <T> T register(String name, Class<T> tClass, Object... args) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(tClass);

        if (args.length > 0) {
            for (Object arg : args) {
                beanDefinitionBuilder.addConstructorArgValue(arg);
            }
        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        ConfigurableApplicationContext applicationContext = (ConfigurableApplicationContext) SpringContextUtil.getApplicationContext();
        BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) applicationContext.getBeanFactory();
        beanFactory.registerBeanDefinition(name, beanDefinition);
        return applicationContext.getBean(name, tClass);
    }

    @Override
    public <T> T resolve(Class<T> clazz) {
        return SpringContextUtil.getBean(clazz);
    }

    @Override
    public <T> T resolveByClassName(String className) {
        if(className==null || className.length()<=0) {
            throw new IllegalArgumentException("className为空");
        }

        String beanName = null;

        if(className.length() > 1) {
            beanName = className.substring(0, 1).toLowerCase() + className.substring(1);
        }
        else {
            beanName = className.toLowerCase();
        }
        return (T)SpringContextUtil.getBean(beanName);
    }

    @Override
    public Object resolve(String name) {
        return SpringContextUtil.getBean(name);
    }

    @Override
    public <T> T resolve(String name, Class<T> clazz) {
        return SpringContextUtil.getBean(name, clazz);
    }

    @Override
    public <T> Map<String, T> resolveAll(Class<T> clazz) {
        return SpringContextUtil.getBeans(clazz);
    }
}
