package me.topits.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通用参数校验加密
 * @author QingKe
 * @date 2020-09-14 16:34
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Signature {

    /**
     * 是否签名
     *
     * @return boolean
     */
    boolean isSecret() default false;
}
