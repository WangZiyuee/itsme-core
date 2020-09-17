package me.topits.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author QingKe
 * @date 2020-09-17 14:32
 **/
@Slf4j
@Component
public class PropertyUtil {

    private static Environment environment;

    @Autowired
    protected void setEnvironment(Environment environment) {
        PropertyUtil.environment = environment;
    }

    public static boolean containsProperty(String key) {
        return StringUtils.hasText(key) && environment.containsProperty(key);
    }

    public static String getProperty(String key) {
        return StringUtils.hasText(key) ? environment.getProperty(key) : null;
    }

    public static String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

    public static Integer getIntProperty(String key, int defaultValue) {
        return environment.getProperty(key, Integer.TYPE, defaultValue);
    }

    public static <T> T getProperty(String key, Class<T> targetType, T defaultValue) {
        return environment.getProperty(key, targetType, defaultValue);
    }
}
