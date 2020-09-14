package me.topits.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author QingKe
 * @date 2020-09-14 14:47
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Encrypted {

    /**
     * 加密校验 默认true
     *
     * @return 加密校验
     */
    boolean value() default true;
}
