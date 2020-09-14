package me.topits.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author QingKe
 * @date 2020-09-14 14:49
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NeedLogin {

    /**
     * 是否需要登录 默认true
     *
     * @return 是否需要登录
     */
    boolean value() default true;
}
