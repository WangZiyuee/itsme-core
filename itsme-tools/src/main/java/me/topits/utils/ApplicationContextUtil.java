package me.topits.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

/**
 * Spring 上下文
 *
 * @author QingKe
 * @date 2020-10-20 14:26
 **/
@Component
public class ApplicationContextUtil implements ApplicationContextAware, InitializingBean {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@Nullable ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtil.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() {
        if (applicationContext == null) {
            throw new IllegalArgumentException("ApplicationContext is Null, Please check");
        }
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> tClass) {
        return getApplicationContext().getBean(tClass);
    }

    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

}
