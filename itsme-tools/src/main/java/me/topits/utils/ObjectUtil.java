package me.topits.utils;

import org.apache.commons.lang3.ObjectUtils;

/**
 * @author Wang Ziyue
 * @since 2020/5/21 14:41
 */
public class ObjectUtil extends ObjectUtils {

    public static boolean allNull(final Object... values) {
        if (values == null) {
            return true;
        }

        for (final Object val : values) {
            if (val != null) {
                return false;
            }
        }

        return true;
    }

    public static boolean anyNull(final Object... values) {
        if (values == null) {
            return true;
        }

        for (final Object val : values) {
            if (val == null) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        // false
        System.out.println(allNull(null, 1));
        // true
        System.out.println(allNull(null, null));
        // false
        System.out.println(allNull(1, 1));
        // true
        System.out.println(anyNull(null, null));
        // true
        System.out.println(anyNull(null, 1));
        // false
        System.out.println(anyNull(1, 1));
    }
}
